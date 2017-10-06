package com.example.syed.bakingapp;

/**
 * Created by syed on 2017-08-10.
 */

public class Ingredient {
    private int ID;
    private String Ingredient;
    private String quantity;
    private String measure;
    private int recipe_id;

    public Ingredient(int ID, String ingredient, String quantity, String measure) {
        this.ID = ID;
        Ingredient = ingredient;
        this.quantity = quantity;
        this.measure = measure;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String ingredient) {
        Ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
