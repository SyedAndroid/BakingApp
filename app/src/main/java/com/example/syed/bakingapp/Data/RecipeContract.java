package com.example.syed.bakingapp.Data;


import android.support.annotation.NonNull;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by syed on 2017-08-10.
 */


/**
 * Uses the Schematic (https://github.com/SimonVT/schematic) library to define the columns in a
 * content provider baked by a database
 */

public class RecipeContract {

    public interface RecipeColumns {

        @DataType(DataType.Type.INTEGER)
        @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
        @AutoIncrement
        public static final String COLUMN_ID = "_id";

        @DataType(DataType.Type.TEXT)
        @NotNull
        public static final String COLUMN_NAME = "name";

        @DataType(DataType.Type.INTEGER)
        public static final String COLUMN_SERVINGS = "servings";

        @DataType(DataType.Type.TEXT)
        public static final String COLUMN_IMAGE = "image";

    }

    public interface ingredientColumns {

        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String _ID = "_id";

        @DataType(DataType.Type.INTEGER)
        String RECIPE_ID = "recipe_id";


        @DataType(DataType.Type.TEXT)
        @NotNull
        String INGREDIENTS_NAME = "ingredient";

        @DataType(DataType.Type.TEXT)
        @NonNull
        String MEASURE = "measure";

        @DataType(DataType.Type.TEXT)
        @NonNull
        String QUANTITY = "quantity";

    }

    public interface stepsColumns {
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        String _ID = "_id";

        @DataType(DataType.Type.INTEGER)
        @NotNull
        String RECIPE_ID = "recipe_id";

        @DataType(DataType.Type.TEXT)
        @NotNull
        String SHORT_DESCRIPTION = "short_desc";

        @DataType(DataType.Type.TEXT)
        String DESCRIPTION = "description";

        @DataType(DataType.Type.TEXT)
        String VIDEO_URL = "video_url";

        @DataType(DataType.Type.TEXT)
        String IMAGE_URL = "img_url";
    }
}

