package com.example.syed.bakingapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.syed.bakingapp.Data.RecipeContract;


public class MyIngredientCursorAdapter extends CursorAdapter {
    public MyIngredientCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_ingredient, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = view.findViewById(R.id.ingredient_name);
        TextView measure = view.findViewById(R.id.ingredient_measure);
        int nameIndex = cursor.getColumnIndex(RecipeContract.ingredientColumns.INGREDIENTS_NAME);
        int measureIndex = cursor.getColumnIndex(RecipeContract.ingredientColumns.MEASURE);
        int quantityIndex = cursor.getColumnIndex(RecipeContract.ingredientColumns.QUANTITY);
        String quan = cursor.getString(quantityIndex);
        String meas = cursor.getString(measureIndex);
        String mergeString = " ( " + quan + " " + meas + ") ";
        name.setText(cursor.getString(nameIndex));
        measure.setText(mergeString);
    }
}
