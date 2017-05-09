package parimi.com.bakify.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.model.BakeSteps;

/**
 * Created by nandpa on 5/9/17.
 */

public class BakeUtils {
    public static BakeReceipe convertJsonToObject(JSONObject jsonObject) {
        BakeReceipe bakeReceipe = new BakeReceipe();
        try {
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            JSONArray steps = jsonObject.getJSONArray("steps");
            bakeReceipe.setId(Integer.parseInt(jsonObject.get("id").toString()));
            bakeReceipe.setName(jsonObject.get("name").toString());
            BakeIngredients[] bakeIngredientsArray = new BakeIngredients[ingredients.length()];
            BakeSteps[] bakeStepsArray = new BakeSteps[steps.length()];
            for (int i=0; i < ingredients.length(); i++) {
                BakeIngredients bakeIngredients = new BakeIngredients();
                bakeIngredients.setIngredient(bakeIngredientsArray[i].getIngredient());
                bakeIngredients.setMeasure(bakeIngredientsArray[i].getMeasure());
                bakeIngredients.setQuantity(bakeIngredientsArray[i].getQuantity());
                bakeIngredientsArray[i] = bakeIngredients;
            }

            for (int i=0; i < steps.length(); i++) {
                BakeSteps bakeSteps = new BakeSteps();
                bakeSteps.setId(bakeStepsArray[i].getId());
                bakeSteps.setDescription(bakeStepsArray[i].getDescription());
                bakeSteps.setShortDescription(bakeStepsArray[i].getShortDescription());
                bakeSteps.setThumbnailURL(bakeStepsArray[i].getThumbnailURL());
                bakeSteps.setVideoURL(bakeStepsArray[i].getVideoURL());
                bakeStepsArray[i] = bakeSteps;
            }
            bakeReceipe.setImage(jsonObject.getString("image"));
            bakeReceipe.setServings(Integer.parseInt(jsonObject.getString("servings")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bakeReceipe;

    }
}
