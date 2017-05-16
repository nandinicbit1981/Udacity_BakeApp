package parimi.com.bakify.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import parimi.com.bakify.R;
import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.model.BakeSteps;

/**
 * Created by nandpa on 5/9/17.
 */

public class BakeUtils {
    public static BakeReceipe convertJsonToBakeReceipe(JSONObject jsonObject, Context context) {
        BakeReceipe bakeReceipe = new BakeReceipe();
        try {

            JSONArray ingredients = jsonObject.getJSONArray(context.getResources().getString(R.string.ingredients_param));
            JSONArray steps = jsonObject.getJSONArray(context.getResources().getString(R.string.steps_param));
            bakeReceipe.setId(Integer.parseInt(jsonObject.get(context.getResources().getString(R.string.id_param)).toString()));
            bakeReceipe.setName(jsonObject.get(context.getResources().getString(R.string.name_param)).toString());
            ArrayList<BakeIngredients> bakeIngredientsArray = new ArrayList<>();
            ArrayList<BakeSteps> bakeStepsArray = new ArrayList<>();

            for (int i=0; i < ingredients.length(); i++) {
                BakeIngredients bakeIngredients = new BakeIngredients();
                JSONObject ingredientObj = (JSONObject) ingredients.get(i);
                bakeIngredients.setIngredient(ingredientObj.get(context.getResources().getString(R.string.ingredient_param)).toString());
                bakeIngredients.setMeasure(ingredientObj.get(context.getResources().getString(R.string.measure_param)).toString());
                bakeIngredients.setQuantity(Float.parseFloat(ingredientObj.get(context.getResources().getString(R.string.quantity_param)).toString()));
                bakeIngredientsArray.add(bakeIngredients);
            }

            for (int i=0; i < steps.length(); i++) {
                BakeSteps bakeSteps = new BakeSteps();
                JSONObject stepsObj = (JSONObject) steps.get(i);
                bakeSteps.setId(Integer.parseInt(stepsObj.get(context.getResources().getString(R.string.id_param)).toString()));
                bakeSteps.setDescription(stepsObj.get(context.getResources().getString(R.string.description_param)).toString());
                bakeSteps.setShortDescription(stepsObj.get(context.getResources().getString(R.string.short_description_param)).toString());
                bakeSteps.setThumbnailURL(stepsObj.getString(context.getResources().getString(R.string.thumbnail_url)).toString());
                bakeSteps.setVideoURL(stepsObj.getString(context.getResources().getString(R.string.video_url)));
                bakeStepsArray.add(bakeSteps);
            }
            bakeReceipe.setIngredients(bakeIngredientsArray);
            bakeReceipe.setSteps(bakeStepsArray);
            bakeReceipe.setImage(jsonObject.getString(context.getResources().getString(R.string.image)));
            bakeReceipe.setServings(Integer.parseInt(jsonObject.getString(context.getResources().getString(R.string.servings))));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bakeReceipe;

    }

    public static BakeSteps convertJsonToSteps(JSONObject jsonObject, Context context) {
        BakeSteps step= new BakeSteps();
        try {
            String id = jsonObject.get(context.getResources().getString(R.string.id_param)).toString();
            String shortDescription = jsonObject.get(context.getResources().getString(R.string.short_description_param)).toString();
            String description = jsonObject.get(context.getResources().getString(R.string.description_param)).toString();
            String videoUrl = jsonObject.get(context.getResources().getString(R.string.video_url)).toString();
            String thumbnailURL = jsonObject.get(context.getResources().getString(R.string.thumbnail_url)).toString();

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

    public static BakeIngredients convertJsonToIngredients(JSONObject jsonObject, Context context) {
        BakeIngredients ingredients= new BakeIngredients();
        try {
            String quantity = jsonObject.get(context.getResources().getString(R.string.quantity_param)).toString();
            String measure = jsonObject.get(context.getResources().getString(R.string.measure_param)).toString();
            String ingredient = jsonObject.get(context.getResources().getString(R.string.ingredient_param)).toString();

            ingredients.setQuantity(Float.parseFloat(quantity));
            ingredients.setMeasure(measure);
            ingredients.setIngredient(ingredient);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredients;

    }

    public static ArrayList<BakeIngredients> convertJsonToIngredientsList(JSONArray jsonArray, Context context) {
        ArrayList<BakeIngredients> bakeIngredientsList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                BakeIngredients bakeIngredient = convertJsonToIngredients(jsonObject, context);
                bakeIngredientsList.add(bakeIngredient);

            }
        }catch (Exception e) {

        }
        return  bakeIngredientsList;
    }

    public static ArrayList<BakeSteps> convertJsonToStepsList(JSONArray jsonArray, Context context) {
        ArrayList<BakeSteps> bakeStepsList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                BakeSteps step = convertJsonToSteps(jsonObject, context);
                bakeStepsList.add(step);

            }
        }catch (Exception e) {

        }
        return  bakeStepsList;
    }
}
