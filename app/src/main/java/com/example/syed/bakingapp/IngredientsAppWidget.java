package com.example.syed.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.example.syed.bakingapp.Data.RecipeContract;
import com.example.syed.bakingapp.Data.RecipeProvider;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsAppWidget extends AppWidgetProvider {
    private static final String ACTION_SIMPLEAPPWIDGET = "ACTION_BROADCASTWIDGET";
    private static int mCounter = 1;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_app_widget);
        String selectionRecipe = RecipeContract.RecipeColumns.COLUMN_ID + "= " + mCounter;

        String selection = RecipeContract.ingredientColumns.RECIPE_ID + "= " + mCounter;
        Cursor RecipeCursor = context.getContentResolver().query(RecipeProvider.Recipes.CONTENT_URI, null, selectionRecipe, null, null);
        if (RecipeCursor != null) {
            RecipeCursor.moveToFirst();
            int rIndex = RecipeCursor.getColumnIndex(RecipeContract.RecipeColumns.COLUMN_NAME);
            String recipeName = RecipeCursor.getString(rIndex);
            views.setTextViewText(R.id.recipe_title_widget, recipeName);
        }
        Cursor cursor = context.getContentResolver().query(RecipeProvider.Ingredients.CONTENT_URI, null, selection, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(RecipeContract.ingredientColumns.INGREDIENTS_NAME);
            String ingredients = "";
            for (int i = 0; i < cursor.getCount(); i++) {
                ingredients = ingredients.concat(cursor.getString(index) + ", ");
                cursor.moveToNext();
            }

            views.setTextViewText(R.id.ingredients_widget, ingredients);
        }
        Intent intent = new Intent(context, IngredientsAppWidget.class);
        intent.setAction(ACTION_SIMPLEAPPWIDGET);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Instruct the widget manager to update the widget
        views.setOnClickPendingIntent(R.id.next_widget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_SIMPLEAPPWIDGET.equals(intent.getAction())) {
            if (mCounter == 4) {
                mCounter = 0;
            }
            mCounter++;
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_app_widget);

            String selectionRecipe = RecipeContract.RecipeColumns.COLUMN_ID + "= " + mCounter;
            Cursor RecipeCursor = context.getContentResolver().query(RecipeProvider.Recipes.CONTENT_URI, null, selectionRecipe, null, null);
            if (RecipeCursor != null) {
                RecipeCursor.moveToFirst();
                int rIndex = RecipeCursor.getColumnIndex(RecipeContract.RecipeColumns.COLUMN_NAME);
                String recipeName = RecipeCursor.getString(rIndex);
                views.setTextViewText(R.id.recipe_title_widget, recipeName);
                RecipeCursor.close();
            } else
                views.setTextViewText(R.id.recipe_title_widget, "Please open the app the configure widger");

            String selection = RecipeContract.ingredientColumns.RECIPE_ID + "= " + mCounter;
            Cursor cursor = context.getContentResolver().query(RecipeProvider.Ingredients.CONTENT_URI, null, selection, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int index = cursor.getColumnIndex(RecipeContract.ingredientColumns.INGREDIENTS_NAME);
                String ingredients = "";
                for (int i = 0; i < cursor.getCount(); i++) {
                    ingredients = ingredients.concat(cursor.getString(index) + ", ");
                    cursor.moveToNext();
                }
                views.setTextViewText(R.id.ingredients_widget, ingredients);
                ComponentName appWidget = new ComponentName(context, IngredientsAppWidget.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidget, views);
                cursor.close();

            }
        }

    }
}

