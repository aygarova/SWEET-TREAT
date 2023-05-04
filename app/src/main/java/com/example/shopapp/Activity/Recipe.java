package com.example.shopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.models.RecipeModel;
import com.example.shopapp.repository.Database;
import com.example.shopapp.validation.RecipeValidation;

public class Recipe extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private EditText servings;
    private EditText ingredients;
    private Database database = new Database();
    private RecipeValidation recipeValidation = new RecipeValidation();

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getSupportActionBar().setTitle("Добави рецепта");

        name = findViewById(R.id.recipe_title);
        description = findViewById(R.id.recipe_description);
        servings = findViewById(R.id.recipe_servings);
        ingredients = findViewById(R.id.recipe_ingredients_label);

        Button myProfileButton = findViewById(R.id.button);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recipe.this, MyProfile.class);
                startActivity(intent);
                finish();
            }
        });
        Button button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeModel recipeModel = recipeValidation.createRecipe(name, description, servings, ingredients, Recipe.this);
                if (recipeModel != null) {
                    database.addRecipeToDatabase(recipeModel).addOnSuccessListener(documentReference -> {
                                Toast.makeText(Recipe.this, "Рецептата е добавена", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Recipe.this, MyProfile.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Recipe.this, "Проблем при добавянето на рецептата", Toast.LENGTH_SHORT).show();
                            });
                }
            }
});
    }
}