package com.example.syed.bakingapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.syed.bakingapp.Data.RecipeContract;
import com.example.syed.bakingapp.Data.RecipeProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>, RecipeeRVAdapter.RecipeClickListener {

    RecipeeRVAdapter rvAdapter;
    RecyclerView rv;
    ProgressBar progressBar;
    @Nullable
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
        setContentView(R.layout.activity_main);
        getIdlingResource();
        progressBar = (ProgressBar) findViewById(R.id.progress);
        mIdlingResource.setIdleState(false);

        if (findViewById(R.id.recycle_view_tab) == null) {
            rv = (RecyclerView) findViewById(R.id.recycle_view);
            rv.setVisibility(View.GONE);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv.setLayoutManager(mLayoutManager);
        } else {

            rv = (RecyclerView) findViewById(R.id.recycle_view_tab);
            rv.setVisibility(View.GONE);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
            rv.setLayoutManager(mLayoutManager);
        }
        Cursor cursor = getContentResolver().query(RecipeProvider.Recipes.CONTENT_URI, null, null, null, null);

        if (!(cursor.getCount() > 0)) {

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else {
            int id = cursor.getColumnIndex(RecipeContract.RecipeColumns.COLUMN_ID);
            int idName = cursor.getColumnIndex(RecipeContract.RecipeColumns.COLUMN_NAME);
            int servingsIndex = cursor.getColumnIndex(RecipeContract.RecipeColumns.COLUMN_SERVINGS);
            int imgeIndex=cursor.getColumnIndex(RecipeContract.RecipeColumns.COLUMN_IMAGE);
            ArrayList<Recipe> recipes = new ArrayList<>();
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                Recipe recipe = new Recipe(cursor.getInt(id), cursor.getString(idName));
                recipe.setServings(cursor.getInt(servingsIndex));
                recipe.setImage(cursor.getString(imgeIndex));
                recipes.add(recipe);
                cursor.moveToNext();
            }
            cursor.close();
            rvAdapter = new RecipeeRVAdapter(this, recipes);
            rv.setAdapter(rvAdapter);
            rv.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);
            mIdlingResource.setIdleState(true);

        }
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int i, Bundle bundle) {
        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {

        rvAdapter = new RecipeeRVAdapter(this, recipes);
        rv.setAdapter(rvAdapter);
        rv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        mIdlingResource.setIdleState(true);

    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<Recipe>> loader) {

    }


    @Override
    public void recipeeClickLister(int position) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra("pos", position + 1);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
