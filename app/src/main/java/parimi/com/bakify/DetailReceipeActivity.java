package parimi.com.bakify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.utils.BakeUtils;

/**
 * This activity is mainly used for populating details ingredients
 */
public class DetailReceipeActivity extends AppCompatActivity {

    private BakeReceipe bakeRecipe;

    @Bind(R.id.bake_recipe_ingredients_txt)
    TextView ingredientsTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_receipe);
        ButterKnife.bind(this);
        String bakeRecipeJson =  getIntent().getExtras().get(getString(R.string.receipe)).toString();
        try {
            // Construct the data source
            JSONObject bakeRecipeJsonObj = new JSONObject(bakeRecipeJson);
            bakeRecipe = BakeUtils.convertJsonToBakeReceipe(bakeRecipeJsonObj, getBaseContext());
            ingredientsTxt.setText(getString(R.string.ingredients));

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.bake_recipe_ingredients_txt)
    public void onIngredientsClick() {
        Gson gson = new Gson();
        String receipeJson = gson.toJson(bakeRecipe);
        Intent intent = new Intent(DetailReceipeActivity.this, IngredientsActivity.class);
        intent.putExtra(getString(R.string.receipe), receipeJson);
        startActivity(intent);

    }
}
