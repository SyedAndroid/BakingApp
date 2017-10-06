package com.example.syed.bakingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.syed.bakingapp.Data.RecipeContract;
import com.example.syed.bakingapp.Data.RecipeProvider;


public class IngredientFragment extends Fragment {
    int position;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientFragment() {
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        // Set the adapter
        String selection = RecipeContract.ingredientColumns.RECIPE_ID + "= " + position;

        Cursor cursor = getContext().getContentResolver().query(RecipeProvider.Ingredients.CONTENT_URI, null, selection, null, null);
        ListView listView = view.findViewById(R.id.list);
        MyIngredientCursorAdapter myIngredientCursorAdapter = new MyIngredientCursorAdapter(getContext(), cursor);
        listView.setAdapter(myIngredientCursorAdapter);
        return view;
    }

}
