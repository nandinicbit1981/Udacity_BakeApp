package parimi.com.bakify.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.model.BakeSteps;

/**
 * Created by nandpa on 5/9/17.
 */

public class BakeUtils {
    public static BakeReceipe convertJsonToBakeReceipe(JSONObject jsonObject) {
        BakeReceipe bakeReceipe = new BakeReceipe();
        try {
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            JSONArray steps = jsonObject.getJSONArray("steps");
            bakeReceipe.setId(Integer.parseInt(jsonObject.get("id").toString()));
            bakeReceipe.setName(jsonObject.get("name").toString());
            ArrayList<BakeIngredients> bakeIngredientsArray = new ArrayList<>();
            ArrayList<BakeSteps> bakeStepsArray = new ArrayList<>();

            for (int i=0; i < ingredients.length(); i++) {
                BakeIngredients bakeIngredients = new BakeIngredients();
                JSONObject ingredientObj = (JSONObject) ingredients.get(i);
                bakeIngredients.setIngredient(ingredientObj.get("ingredient").toString());
                bakeIngredients.setMeasure(ingredientObj.get("measure").toString());
                bakeIngredients.setQuantity(Float.parseFloat(ingredientObj.get("quantity").toString()));
                bakeIngredientsArray.add(bakeIngredients);
            }

            for (int i=0; i < steps.length(); i++) {
                BakeSteps bakeSteps = new BakeSteps();
                JSONObject stepsObj = (JSONObject) steps.get(i);
                bakeSteps.setId(Integer.parseInt(stepsObj.get("id").toString()));
                bakeSteps.setDescription(stepsObj.get("description").toString());
                bakeSteps.setShortDescription(stepsObj.get("shortDescription").toString());
                bakeSteps.setThumbnailURL(stepsObj.getString("thumbnailURL").toString());
                bakeSteps.setVideoURL(stepsObj.getString("videoURL"));
                bakeStepsArray.add(bakeSteps);
            }
            bakeReceipe.setIngredients(bakeIngredientsArray);
            bakeReceipe.setSteps(bakeStepsArray);
            bakeReceipe.setImage(jsonObject.getString("image"));
            bakeReceipe.setServings(Integer.parseInt(jsonObject.getString("servings")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bakeReceipe;

    }

    public static BakeSteps convertJsonToSteps(JSONObject jsonObject) {
        BakeSteps step= new BakeSteps();
        try {
            String id = jsonObject.get("id").toString();
            String shortDescription = jsonObject.get("shortDescription").toString();
            String description = jsonObject.get("description").toString();
            String videoUrl = jsonObject.get("videoURL").toString();
            String thumbnailURL = jsonObject.get("thumbnailURL").toString();

            step.setId(Integer.parseInt(id));
            step.setDescription(description);
            step.setShortDescription(shortDescription);
            step.setVideoURL(videoUrl);
            step.setThumbnailURL(thumbnailURL);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return step;

    }
}
