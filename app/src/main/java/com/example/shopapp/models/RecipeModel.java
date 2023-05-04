package com.example.shopapp.models;

import java.util.List;

public class RecipeModel {

    private String recipeId;
    private String userKey;
    private String title;
    private String description;
    private String servings;
    private List<String> ingredients;

    public RecipeModel() {
    }

    public RecipeModel(String titleValue, String descriptionValue, String servingsValues, List<String> ingredientsValue) {
        this.title = titleValue;
        this.description = descriptionValue;
        this.servings = servingsValues;
        this.ingredients  = ingredientsValue;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getServings() {
        return servings;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

}

