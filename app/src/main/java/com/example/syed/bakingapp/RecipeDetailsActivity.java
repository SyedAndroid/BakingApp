package com.example.syed.bakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.syed.bakingapp.Data.RecipeContract;
import com.example.syed.bakingapp.Data.RecipeProvider;

public class RecipeDetailsActivity extends AppCompatActivity implements stepsFragment.OnListFragmentInteractionListener {
    Boolean tab = false;
    int position;

    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        position = getIntent().getIntExtra("pos", 0);

        final int pos = position;
        final TextView ingredientTextView = (TextView) findViewById(R.id.ingredients);
        getIdlingResource();
        mIdlingResource.setIdleState(false);
        ingredientTextView.setText("Ingredients");


        stepsFragment steps = new stepsFragment();
        steps.setPosition(position);
        FragmentManager stepFragment = getSupportFragmentManager();

        stepFragment.beginTransaction()
                .replace(R.id.steps_fragment, steps)
                .commit();

        if (findViewById(R.id.step_tab_layout) != null) {
            tab = true;
            setIngredients();
            ingredientTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientTextView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    setIngredients();
                }
            });
        } else {
            ingredientTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), IngredientListActivity.class);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });
        }
        mIdlingResource.setIdleState(true);
    }

    public void setIngredients() {
        IngredientFragment ingredientFragment = new IngredientFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        ingredientFragment.setPosition(position);
        fragmentManager.beginTransaction()
                .replace(R.id.detail_fragment_tab, ingredientFragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(int id) {
        if (tab) {
            String selection = RecipeContract.stepsColumns._ID + "= " + id;

            Cursor cursor = getContentResolver().query(RecipeProvider.Steps.CONTENT_URI, null, selection, null, null);
            cursor.moveToFirst();
            int desc = cursor.getColumnIndex(RecipeContract.stepsColumns.DESCRIPTION);
            int url = cursor.getColumnIndex(RecipeContract.stepsColumns.VIDEO_URL);
            int img = cursor.getColumnIndex(RecipeContract.stepsColumns.IMAGE_URL);

            String description = cursor.getString(desc);
            String URL = cursor.getString(url);
            String imgUrl = cursor.getString(img);

            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setVariables(URL, description, imgUrl);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment_tab, videoFragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
