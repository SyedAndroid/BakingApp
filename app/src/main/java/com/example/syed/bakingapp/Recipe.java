package com.example.syed.bakingapp;

/**
 * Created by syed on 2017-08-07.
 */

public class Recipe {
    private int id;
    private String name;
    private int servings;
    private String imageUrl;

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String image) {
        this.imageUrl = image;
    }
}
