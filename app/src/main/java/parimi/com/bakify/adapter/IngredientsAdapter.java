package parimi.com.bakify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import parimi.com.bakify.R;
import parimi.com.bakify.model.BakeIngredients;

/**
 * Created by nandpa on 5/9/17.
 */
public class IngredientsAdapter extends ArrayAdapter<BakeIngredients> {
    public IngredientsAdapter(Context context, ArrayList<BakeIngredients> ingredients) {
        super(context, 0, ingredients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        BakeIngredients ingredients = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredients_detail_view, parent, false);
        }
        // Lookup view for data population
        TextView ingredientNameTxt = (TextView) convertView.findViewById(R.id.ingredients_name_txt_view);
        TextView ingredientsMeasurementTxt = (TextView) convertView.findViewById(R.id.ingredients_measurements_txt_view);

        // Populate the data into the template view using the data object
        ingredientNameTxt.setText(ingredients.getIngredient());
        ingredientsMeasurementTxt.setText(ingredients.getQuantity() + " " + ingredients.getMeasure());

        // Return the completed view to render on screen
        return convertView;
    }
}