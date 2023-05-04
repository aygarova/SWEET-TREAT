package com.example.shopapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.models.RecipeModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecipeAdapter extends FirestoreRecyclerAdapter<RecipeModel, RecipeAdapter.RecipeViewHolder> {

        public RecipeAdapter(@NonNull FirestoreRecyclerOptions<RecipeModel> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull RecipeModel model) {
            holder.nameTextView.setText(model.getTitle());
            holder.descriptionTextView.setText(model.getDescription());
            holder.servingTextView.setText(String.valueOf(model.getServings()));
            holder.ingredientsTextView.setText(String.valueOf(model.getIngredients()));
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_all_recipe, parent, false);
            return new RecipeViewHolder(view);
        }

        public static class RecipeViewHolder extends RecyclerView.ViewHolder {

            TextView nameTextView;
            TextView descriptionTextView;
            TextView servingTextView;
            TextView ingredientsTextView;

            public RecipeViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.recipe_name);
                descriptionTextView = itemView.findViewById(R.id.recipe_description);
                servingTextView = itemView.findViewById(R.id.recipe_servings);
                ingredientsTextView = itemView.findViewById(R.id.recipe_ingredients);
            }
        }
    }