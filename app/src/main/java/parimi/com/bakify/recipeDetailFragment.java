package parimi.com.bakify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import parimi.com.bakify.adapter.IngredientsAdapter;
import parimi.com.bakify.dummy.DummyContent;
import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeSteps;
import parimi.com.bakify.utils.BakeUtils;

import static parimi.com.bakify.R.string.ingredients;

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
    private ArrayList<BakeIngredients> ingredients;

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
            String ingredientsJson = getArguments().getString("ingredients");


            if(ingredientsJson != null) {
                JSONArray ingredientJsonArray = new JSONArray(ingredientsJson);
                ingredients = BakeUtils.convertJsonToIngredientsList(ingredientJsonArray);

            } else {
                JSONObject stepJsonObj = new JSONObject(stepJson);
                step = BakeUtils.convertJsonToSteps(stepJsonObj);
            }


        }catch (Exception e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        if(ingredients != null) {
            rootView = inflater.inflate(R.layout.activity_ingredients, container, false);
            // Create the adapter to convert the array to views
            IngredientsAdapter adapter = new IngredientsAdapter(getContext(), ingredients);
            // Attach the adapter to a ListView
            ListView listView = (ListView) rootView.findViewById(R.id.ingredients_listview);
            listView.setAdapter(adapter);
        } else {

            // Show the dummy content as text in a TextView.
            if (mItem != null) {
                ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(mItem.details);
            }

            if (step != null) {
                ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(step.getDescription());
            }

        }
        return rootView;
    }
}
