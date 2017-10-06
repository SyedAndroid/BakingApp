package com.example.syed.bakingapp.Utils;


import com.example.syed.bakingapp.Ingredient;
import com.example.syed.bakingapp.Recipe;
import com.example.syed.bakingapp.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by syed on 2017-08-07.
 */

public class JSONUtils {

    public static ArrayList<Recipe> parseJSONStringRecipe(String JSONString) {

        ArrayList<Recipe> resultList = new ArrayList<>();

        try {

            JSONArray resultArray = new JSONArray(JSONString);

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject recipeeJSON = resultArray.getJSONObject(i);
                int id = recipeeJSON.getInt("id");
                String title = recipeeJSON.getString("name");
                Recipe recipe = new Recipe(id, title);
                int servings = recipeeJSON.getInt("servings");
                recipe.setServings(servings);
                String img = ""+recipeeJSON.getString("image");;
                 recipe.setImage(img);

                resultList.add(recipe);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return resultList;

    }

    public static ArrayList<Ingredient> parseJSONStringIngredient(String JSONString) {

        ArrayList<Ingredient> resultList = new ArrayList<>();

        try {

            JSONArray resultArray = new JSONArray(JSONString);

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject recipeeJSON = resultArray.getJSONObject(i);
                int id = recipeeJSON.getInt("id");
                String title = recipeeJSON.getString("name");
                JSONArray jsonArray = recipeeJSON.getJSONArray("ingredients");
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject ingJSON = jsonArray.getJSONObject(j);
                    Ingredient ingredient = new Ingredient(id, ingJSON.getString("ingredient"), ingJSON.getString("quantity"), ingJSON.getString("measure"));
                    resultList.add(ingredient);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return resultList;

    }

    public static ArrayList<Step> parseJSONStringSteps(String JSONString) {

        ArrayList<Step> resultList = new ArrayList<>();

        try {

            JSONArray resultArray = new JSONArray(JSONString);

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject recipeeJSON = resultArray.getJSONObject(i);
                int id = recipeeJSON.getInt("id");
                JSONArray jsonArray = recipeeJSON.getJSONArray("steps");
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject stepJSON = jsonArray.getJSONObject(j);
                    Step step = new Step(id, stepJSON.getString("shortDescription"));
                    String videoURL = stepJSON.getString("videoURL");
                    String desc = stepJSON.getString("description");
                    String thumbnail =""+ stepJSON.getString("thumbnailURL");

                    if (!videoURL.isEmpty()) {
                        step.setVideoUrl(videoURL);
                    }
                    if (!desc.isEmpty()) {
                        step.setDescription(desc);
                    }
                    if (!thumbnail.isEmpty()) {
                        step.setImgURL(thumbnail);
                    }
                    resultList.add(step);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return resultList;

    }


}
