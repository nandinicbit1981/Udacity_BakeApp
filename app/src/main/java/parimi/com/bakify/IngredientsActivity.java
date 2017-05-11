package parimi.com.bakify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import parimi.com.bakify.adapter.IngredientsAdapter;
import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.utils.BakeUtils;

public class IngredientsActivity extends AppCompatActivity {

    BakeReceipe bakeReceipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        String bakeRecipeJson =  getIntent().getExtras().get(getString(R.string.receipe)).toString();
        try {
            // Construct the data source
            JSONObject bakeRecipeJsonObj = new JSONObject(bakeRecipeJson);
            bakeReceipe = BakeUtils.convertJsonToBakeReceipe(bakeRecipeJsonObj);
            ArrayList<BakeIngredients> ingredients = bakeReceipe.getIngredients();
            // Create the adapter to convert the array to views
            IngredientsAdapter adapter = new IngredientsAdapter(this, ingredients);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.ingredients_listview);
            listView.setAdapter(adapter);

        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
