package com.example.shopapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.models.RecipeModel;

import java.util.List;


public class MyAdapters extends RecyclerView.Adapter<MyAdapters.ViewHolder> {
        private List<RecipeModel> myObjectList;

        public MyAdapters(List<RecipeModel> myObjectList) {
            this.myObjectList = myObjectList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_all_recipe, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RecipeModel model = myObjectList.get(position);

            holder.nameTextView.setText(model.getTitle());
            holder.descriptionTextView.setText(model.getDescription());
            holder.servingTextView.setText(String.valueOf(model.getServings()));
            holder.ingredientsTextView.setText(String.valueOf(model.getIngredients()));
        }

        @Override
        public int getItemCount() {
            return myObjectList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView;
            TextView descriptionTextView;
            TextView servingTextView;
            TextView ingredientsTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.recipe_name);
                descriptionTextView = itemView.findViewById(R.id.recipe_description);
                servingTextView = itemView.findViewById(R.id.recipe_servings);
                ingredientsTextView = itemView.findViewById(R.id.recipe_ingredients);
            }
        }
}