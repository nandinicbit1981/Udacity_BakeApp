package parimi.com.bakify.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.data;

/**
 * Created by nandpa on 5/9/17.
 */

public class BakeReceipe {
    private  int id;
    private  String name;
    private  BakeIngredients[] ingredients;
    private  BakeSteps[] bakeSteps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BakeIngredients[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(BakeIngredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public BakeSteps[] getBakeSteps() {
        return bakeSteps;
    }

    public void setBakeSteps(BakeSteps[] bakeSteps) {
        this.bakeSteps = bakeSteps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
