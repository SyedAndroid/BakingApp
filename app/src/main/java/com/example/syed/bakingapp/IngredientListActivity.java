package com.example.syed.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class IngredientListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        IngredientFragment ingredientFragment = new IngredientFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        ingredientFragment.setPosition(position);
        fragmentManager.beginTransaction()
                .add(R.id.ingredient_fragment, ingredientFragment)
                .commit();
    }
}
