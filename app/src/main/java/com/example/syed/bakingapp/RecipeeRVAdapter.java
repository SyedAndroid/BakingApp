package com.example.syed.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by syed on 2017-08-08.
 */

public class RecipeeRVAdapter extends RecyclerView.Adapter<RecipeeRVAdapter.RecipeViewHolder> {

    Recipe recipe;
    Context context;
    private ArrayList<Recipe> recipes;
    private RecipeClickListener recipeClickListener;


    public RecipeeRVAdapter(RecipeClickListener recipeClickListener, ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.recipeClickListener = recipeClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        recipe = recipes.get(position);
        holder.servings.setText(String.valueOf(recipe.getServings()));

        if ((!TextUtils.equals(recipe.getImage() ,""))) {
            Picasso.with(context).load(recipe.getImage()).into(holder.recipeImage);
        }
        holder.recipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public interface RecipeClickListener {
        void recipeeClickLister(int position);

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipeImage;
        TextView recipeName;
        TextView servings;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.list_item_icon);
            recipeName = itemView.findViewById(R.id.recipe_name);
            servings = itemView.findViewById(R.id.servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            recipeClickListener.recipeeClickLister(adapterPosition);

        }
    }
}
