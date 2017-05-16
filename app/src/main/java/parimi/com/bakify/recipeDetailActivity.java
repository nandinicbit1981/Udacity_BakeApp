package parimi.com.bakify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * An activity representing a single recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link recipeListActivity}.
 */
public class recipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(getString(R.string.steps_param), getIntent().getStringExtra(getString(R.string.steps_param)));
            arguments.putString(getString(R.string.current_step), getIntent().getStringExtra(getString(R.string.current_step)));
            arguments.putString(getString(R.string.ingredients_param), getIntent().getStringExtra(getString(R.string.ingredients_param)));

            recipeDetailFragment fragment = new recipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, recipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
