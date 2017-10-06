package com.example.syed.bakingapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.syed.bakingapp.Data.RecipeContract;
import com.example.syed.bakingapp.Data.RecipeProvider;
import com.example.syed.bakingapp.Utils.JSONUtils;
import com.example.syed.bakingapp.Utils.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by syed on 2017-08-07.
 */

public class RecipeLoader extends AsyncTaskLoader<ArrayList<Recipe>> {
    public RecipeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Recipe> loadInBackground() {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ArrayList<Step> stepsArrayList = new ArrayList<>();

        URL url;
        url = NetworkUtil.createUrl();


        try {
            String JSONString = NetworkUtil.getResponseFromHttpUrl(url);
            recipeArrayList = JSONUtils.parseJSONStringRecipe(JSONString);
            ingredientArrayList = JSONUtils.parseJSONStringIngredient(JSONString);
            stepsArrayList = JSONUtils.parseJSONStringSteps(JSONString);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor cursor = getContext().getContentResolver().query(RecipeProvider.Recipes.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() <= 0) {
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < recipeArrayList.size(); i++) {
                Recipe recipe = recipeArrayList.get(i);
                contentValues.put(RecipeContract.RecipeColumns.COLUMN_NAME, recipe.getName());
                contentValues.put(RecipeContract.RecipeColumns.COLUMN_SERVINGS, recipe.getServings());
                contentValues.put(RecipeContract.RecipeColumns.COLUMN_IMAGE, recipe.getImage());
                getContext().getContentResolver().insert(RecipeProvider.Recipes.CONTENT_URI, contentValues);
            }
            contentValues.clear();
            for (int j = 0; j < ingredientArrayList.size(); j++) {
                Ingredient ingredient = ingredientArrayList.get(j);
                contentValues.put(RecipeContract.ingredientColumns.RECIPE_ID, ingredient.getID());
                contentValues.put(RecipeContract.ingredientColumns.INGREDIENTS_NAME, ingredient.getIngredient());
                contentValues.put(RecipeContract.ingredientColumns.MEASURE, ingredient.getMeasure());
                contentValues.put(RecipeContract.ingredientColumns.QUANTITY, ingredient.getQuantity());
                getContext().getContentResolver().insert(RecipeProvider.Ingredients.CONTENT_URI, contentValues);
            }
            contentValues.clear();
            for (int j = 0; j < stepsArrayList.size(); j++) {
                Step step = stepsArrayList.get(j);
                contentValues.put(RecipeContract.stepsColumns.RECIPE_ID, step.getID());
                contentValues.put(RecipeContract.stepsColumns.SHORT_DESCRIPTION, step.getShortDescription());
                contentValues.put(RecipeContract.stepsColumns.DESCRIPTION, step.getDescription());
                contentValues.put(RecipeContract.stepsColumns.VIDEO_URL, step.getVideoUrl());
                contentValues.put(RecipeContract.stepsColumns.IMAGE_URL, step.getImgURL());
                getContext().getContentResolver().insert(RecipeProvider.Steps.CONTENT_URI, contentValues);
            }
        }
        return recipeArrayList;
    }
}
