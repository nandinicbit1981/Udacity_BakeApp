package parimi.com.bakify;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import parimi.com.bakify.dummy.DummyContent;
import parimi.com.bakify.model.BakeSteps;
import parimi.com.bakify.utils.BakeUtils;

/**
 * A fragment representing a single recipe detail screen.
 * This fragment is either contained in a {@link recipeListActivity}
 * in two-pane mode (on tablets) or a {@link recipeDetailActivity}
 * on handsets.
 */
public class recipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    private BakeSteps step;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public recipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String stepJson = getArguments().getString("steps");

            JSONObject stepJsonObj = new JSONObject(stepJson);
            step = BakeUtils.convertJsonToSteps(stepJsonObj);

        }catch (Exception e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(mItem.details);
        }

        if(step != null) {
            ((TextView)rootView.findViewById(R.id.recipe_detail)).setText(step.getDescription());
        }

        return rootView;
    }
}
