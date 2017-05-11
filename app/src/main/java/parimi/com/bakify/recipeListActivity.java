package parimi.com.bakify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;

        String bakeRecipeJson =  getIntent().getExtras().get(getString(R.string.receipe)).toString();
        try {
            // Construct the data source
            JSONObject bakeRecipeJsonObj = new JSONObject(bakeRecipeJson);
            BakeReceipe bakeRecipe = BakeUtils.convertJsonToBakeReceipe(bakeRecipeJsonObj);
             bakeSteps =  bakeRecipe.getSteps();
            setupRecyclerView((RecyclerView) recyclerView);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(bakeSteps));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<BakeSteps> steps = null;

        public SimpleItemRecyclerViewAdapter(List<BakeSteps> items) {
            steps = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = steps.get(position);
            //holder.mIdView.setText(steps.get(position).getId());
            holder.mContentView.setText(steps.get(position).getDescription());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String receipeJson = gson.toJson(holder.mItem);
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        //arguments.putString(recipeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        arguments.putString("steps", receipeJson);
                        recipeDetailFragment fragment = new recipeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, recipeDetailActivity.class);
                        intent.putExtra("steps", receipeJson);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return steps.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public BakeSteps mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
