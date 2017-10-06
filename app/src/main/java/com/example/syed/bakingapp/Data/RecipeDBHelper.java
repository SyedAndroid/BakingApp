package com.example.syed.bakingapp.Data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by syed on 2017-08-10.
 */

/**
 * Uses the Schematic (https://github.com/SimonVT/schematic) library to create a database with one
 * table for messages
 */

@Database(version = RecipeDBHelper.VERSION)
public class RecipeDBHelper {

    public static final int VERSION = 1;

    @Table(RecipeContract.RecipeColumns.class)
    public static final String RECIPE = "recipes";

    @Table(RecipeContract.ingredientColumns.class)
    public static final String INGREDIENT = "ingredients";

    @Table(RecipeContract.stepsColumns.class)
    public static final String STEPS = "steps";
}