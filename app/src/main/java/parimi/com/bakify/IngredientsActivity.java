package parimi.com.bakify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import parimi.com.bakify.adapter.IngredientsAdapter;
import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.utils.BakeUtils;

/**
 * This activity populates ingredients on the ingredients details view.
 */
public class IngredientsActivity extends AppCompatActivity {

    private static final String TAG = IngredientsActivity.class.getSimpleName();

    @Bind(R.id.ingredients_listview)
    ListView ingredientsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ButterKnife.bind(this);

        try {
            String bakeRecipeJson = getIntent().getExtras().get(getString(R.string.receipe)).toString();
            JSONObject bakeRecipeJsonObj = new JSONObject(bakeRecipeJson);
            BakeReceipe bakeReceipe = BakeUtils.convertJsonToBakeReceipe(bakeRecipeJsonObj, getBaseContext());
            ArrayList<BakeIngredients> ingredients = bakeReceipe.getIngredients();

            // Create the adapter to convert the array to views
            IngredientsAdapter adapter = new IngredientsAdapter(this, ingredients);

            // Attach the adapter to a ListView
            ingredientsListView.setAdapter(adapter);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

    }
}
