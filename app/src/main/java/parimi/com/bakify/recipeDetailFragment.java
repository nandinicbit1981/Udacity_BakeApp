package parimi.com.bakify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parimi.com.bakify.adapter.IngredientsAdapter;
import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeSteps;
import parimi.com.bakify.utils.BakeUtils;

/**
 * A fragment representing a single recipe detail screen.
 * This fragment is either contained in a {@link recipeListActivity}
 * in two-pane mode (on tablets) or a {@link recipeDetailActivity}
 * on handsets.
 */
public class recipeDetailFragment extends Fragment implements ExoPlayer.EventListener{


    @Nullable
    @Bind(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @Nullable
    @Bind(R.id.ingredients_listview)
    ListView ingredientsListView;

    @Nullable
    @Bind(R.id.recipe_detail)
    TextView recipeDetailText;

    @Nullable
    @Bind(R.id.prev_step)
    ImageView prevStep;

    @Nullable
    @Bind(R.id.next_step)
    ImageView nextStep;

    @Nullable
    @Bind(R.id.navigate_steps)
    LinearLayout navigteStepsLayout;


    private BakeSteps step;
    private ArrayList<BakeIngredients> ingredients;
    private ArrayList<BakeSteps> bakeStepsList;

    private SimpleExoPlayer mExoPlayer;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private static final String TAG = recipeDetailFragment.class.getSimpleName();
    String stepListJson = "";
    String stepJson = "";
    String ingredientsJson = "";
    Boolean navigateSteps = false;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public recipeDetailFragment() {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
             stepListJson = getArguments().getString("steps");
             stepJson = getArguments().getString("currentStep");
             ingredientsJson = getArguments().getString("ingredients");
             Boolean navigateSteps = getArguments().getBoolean("navigateSteps");

            if(ingredientsJson != null) {
                JSONArray ingredientJsonArray = new JSONArray(ingredientsJson);
                ingredients = BakeUtils.convertJsonToIngredientsList(ingredientJsonArray);

            } else {
                JSONObject stepJsonObj = new JSONObject(stepJson);
                JSONArray stepJsonArray = new JSONArray(stepListJson);
                step = BakeUtils.convertJsonToSteps(stepJsonObj);
                bakeStepsList = BakeUtils.convertJsonToStepsList(stepJsonArray);
            }


        }catch (Exception e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;

        if(ingredients != null) {
            rootView = inflater.inflate(R.layout.activity_ingredients, container, false);
            ButterKnife.bind(this, rootView);
            // Create the adapter to convert the array to views
            IngredientsAdapter adapter = new IngredientsAdapter(getContext(), ingredients);
            // Attach the adapter to a ListView

            ingredientsListView.setAdapter(adapter);
        } else {

            rootView = inflater.inflate(R.layout.recipe_detail, container, false);

            ButterKnife.bind(this, rootView);
            if(navigateSteps) {
                navigteStepsLayout.setVisibility(View.INVISIBLE);
            } else {
                navigteStepsLayout.setVisibility(View.VISIBLE);
            }
            // Initialize the Media Session.
            initializeMediaSession();

            if (step != null) {
                initializePlayer(Uri.parse(step.getVideoURL()));
                recipeDetailText.setText(step.getDescription());
            }

        }
        return rootView;
    }




    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }




    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Bakify");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if(mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }


        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }

    @Nullable
    @OnClick(R.id.prev_step)
    public void prevStep(){
        releasePlayer();
        Context context = getContext();
        Intent intent = new Intent(context, recipeDetailActivity.class);
        intent.putExtra("steps", stepListJson);
        int stepId = step.getId();
        if(stepId != 0) {
            stepId -=1;
        }

        step = bakeStepsList.get(stepId);
        Gson gson = new Gson();
        intent.putExtra("currentStep", gson.toJson(step));

        context.startActivity(intent);
    }

    @Nullable
    @OnClick(R.id.next_step)
    public void nextStep(){
        releasePlayer();
        Context context = getContext();
        Intent intent = new Intent(context, recipeDetailActivity.class);
        intent.putExtra("steps", stepListJson);
        int stepId = step.getId();
        if(stepId < bakeStepsList.size()) {
            stepId +=1;
        }

        step = bakeStepsList.get(stepId);
        Gson gson = new Gson();
        intent.putExtra("currentStep", gson.toJson(step));

        context.startActivity(intent);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
