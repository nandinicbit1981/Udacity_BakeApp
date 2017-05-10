package parimi.com.bakify.model;

import java.util.ArrayList;

/**
 * Created by nandpa on 5/9/17.
 */

public class BakeReceipe {
    private  int id;
    private  String name;
    private  ArrayList<BakeIngredients> ingredients;
    private  ArrayList<BakeSteps> steps;
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

    public ArrayList<BakeIngredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<BakeIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<BakeSteps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<BakeSteps> steps) {
        this.steps = steps;
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
