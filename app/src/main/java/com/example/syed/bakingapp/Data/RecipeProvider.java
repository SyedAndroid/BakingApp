package com.example.syed.bakingapp.Data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by syed on 2017-08-10.
 */


/**
 * Uses the Schematic (https://github.com/SimonVT/schematic) to create a content provider and
 * define
 * URIs for the provider
 */

@ContentProvider(
        authority = RecipeProvider.AUTHORITY,
        database = RecipeDBHelper.class)
public final class RecipeProvider {

    public static final String AUTHORITY = "com.example.syed.bakingapp.Data.RecipeProvider";


    @TableEndpoint(table = RecipeDBHelper.RECIPE)
    public static class Recipes {

        @ContentUri(
                path = "recipes",
                type = "vnd.android.cursor.dir/recipes",
                defaultSort = RecipeContract.RecipeColumns.COLUMN_ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");
    }

    @TableEndpoint(table = RecipeDBHelper.INGREDIENT)
    public static class Ingredients {

        @ContentUri(
                path = "ingredients",
                type = "vnd.android.cursor.dir/ingredients",
                defaultSort = RecipeContract.ingredientColumns._ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ingredients");
    }

    @TableEndpoint(table = RecipeDBHelper.STEPS)
    public static class Steps {

        @ContentUri(
                path = "steps",
                type = "vnd.android.cursor.dir/steps",
                defaultSort = RecipeContract.ingredientColumns._ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/steps");
    }
}