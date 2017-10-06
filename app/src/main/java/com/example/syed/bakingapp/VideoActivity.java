package com.example.syed.bakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.syed.bakingapp.Data.RecipeContract;
import com.example.syed.bakingapp.Data.RecipeProvider;

public class VideoActivity extends AppCompatActivity {
    int id;
    int recipeID;
    VideoFragment videoFragment;
    private final static String  SAVED_FRAGMENT= "video_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        id = getIntent().getIntExtra("id", 0);
        recipeID = getIntent().getIntExtra("position", 0);

        String selection = RecipeContract.stepsColumns._ID + "= " + id;

        Cursor cursor = getContentResolver().query(RecipeProvider.Steps.CONTENT_URI, null, selection, null, null);
        cursor.moveToFirst();
        int desc = cursor.getColumnIndex(RecipeContract.stepsColumns.DESCRIPTION);
        int url = cursor.getColumnIndex(RecipeContract.stepsColumns.VIDEO_URL);
        int img = cursor.getColumnIndex(RecipeContract.stepsColumns.IMAGE_URL);
        String description = cursor.getString(desc);
        String URL = cursor.getString(url);
        String imgUrl = cursor.getString(img);

        if (savedInstanceState == null) {
            videoFragment = new VideoFragment();

        } else {
            videoFragment = (VideoFragment) getSupportFragmentManager().getFragment(savedInstanceState,SAVED_FRAGMENT);
        }

        videoFragment.setVariables(URL, description, imgUrl);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.video_fragment, videoFragment)
                .commit();

        Button next = (Button) findViewById(R.id.next);
        Button previous = (Button) findViewById(R.id.previous);
        String recipeeSelection = RecipeContract.stepsColumns.RECIPE_ID + "=" + recipeID;
        Cursor recipeCursor = getContentResolver().query(RecipeProvider.Steps.CONTENT_URI, null, recipeeSelection, null, null);
        recipeCursor.moveToFirst();
        int index = recipeCursor.getColumnIndex(RecipeContract.stepsColumns._ID);
        int previousLimit = recipeCursor.getInt(index);
        recipeCursor.moveToLast();
        int nextLimit = recipeCursor.getInt(index);
        if (id == nextLimit) {
            next.setEnabled(false);
        }
        if (id == previousLimit) {
            previous.setEnabled(false);
        }

    }

    public void NextFunction(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("id", id + 1);
        intent.putExtra("position", recipeID);

        startActivity(intent);
    }

    public void PreviousFunction(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("id", id - 1);
        intent.putExtra("position", recipeID);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, SAVED_FRAGMENT, videoFragment);

    }
}
