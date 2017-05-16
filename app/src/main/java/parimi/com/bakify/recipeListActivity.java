package parimi.com.bakify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.model.BakeSteps;
import parimi.com.bakify.utils.BakeUtils;

/**
 * An activity representing a list of recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link recipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class recipeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<BakeSteps> bakeSteps;
    private List<BakeIngredients> bakeIngredients;

    @Bind(R.id.recipe_list)
    View recyclerView;

    @Nullable
    @Bind(R.id.recipe_detail_container)
    FrameLayout recipeDetailContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        String bakeRecipeJson =  getIntent().getExtras().get(getString(R.string.recipe_param)).toString();
        try {
            // Construct the data source
            JSONObject bakeRecipeJsonObj = new JSONObject(bakeRecipeJson);
            BakeReceipe bakeRecipe = BakeUtils.convertJsonToBakeReceipe(bakeRecipeJsonObj, getBaseContext());

            bakeSteps =  bakeRecipe.getSteps();
            bakeIngredients = bakeRecipe.getIngredients();

            Gson gson = new Gson();
            String ingredientsJson = gson.toJson(bakeIngredients);

            //sending to widget
            Intent broadCastIntent = new Intent(getApplicationContext(),BakifyWidget.class);
            broadCastIntent.putExtra(getString(R.string.ingredients_param), ingredientsJson);
            broadCastIntent.putExtra(getString(R.string.recipe_param),  bakeRecipe.getName());
            getApplicationContext().sendBroadcast(broadCastIntent);
            setupRecyclerView((RecyclerView) recyclerView);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        if (recipeDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(bakeSteps, bakeIngredients));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<BakeSteps> steps = null;
        private List<BakeIngredients> ingredients = null;

        public SimpleItemRecyclerViewAdapter(List<BakeSteps> steps, List<BakeIngredients> ingredients) {

            this.steps = steps;
            this.ingredients = ingredients;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if(position == 0) {
                holder.mIngredients = ingredients;
                holder.mContentView.setText(getString(R.string.ingredients));
            } else {
                holder.mStep = steps.get(position - 1);
                holder.mSteps = steps;
                holder.mContentView.setText(steps.get(position - 1).getShortDescription());
            }
            //holder.mIdView.setText(steps.get(position).getId());


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();

                    String stepsJson = gson.toJson(holder.mSteps);
                    String currentJson = gson.toJson(holder.mStep);
                    String ingredientsJson = gson.toJson(holder.mIngredients);


                    if (mTwoPane) {
                        Bundle arguments = new Bundle();

                        if(position == 0) {
                            arguments.putString(getString(R.string.ingredients_param), ingredientsJson);
                        } else {
                            arguments.putString(getString(R.string.steps_param), stepsJson);
                            arguments.putString(getString(R.string.current_step), currentJson);
                            arguments.putBoolean(getString(R.string.navigate_steps), mTwoPane);
                        }

                        recipeDetailFragment fragment = new recipeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, recipeDetailActivity.class);

                        if(position == 0) {
                            intent.putExtra(getString(R.string.ingredients_param), ingredientsJson);

                        } else {
                            intent.putExtra(getString(R.string.steps_param), stepsJson);
                            intent.putExtra(getString(R.string.current_step), currentJson);
                            intent.putExtra(getString(R.string.navigate_steps), mTwoPane);
                        }
                        context.startActivity(intent);
                    }
                }
            });
        }



        @Override
        public int getItemCount() {
            if(steps !=  null) {
                return steps.size() + 1;
            }
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public BakeSteps mStep;
            public List<BakeSteps> mSteps;
            public List<BakeIngredients> mIngredients;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
