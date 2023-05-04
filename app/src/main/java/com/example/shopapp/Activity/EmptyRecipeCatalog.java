package com.example.shopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopapp.R;
import com.example.shopapp.models.SingInModel;

public class EmptyRecipeCatalog extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_recipe_catalog);

         Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmptyRecipeCatalog.this, Recipe.class);
                startActivity(intent);
                finish();

            }
        });

        Button myProfile = findViewById(R.id.my_profile_button);

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmptyRecipeCatalog.this, MyProfile.class);
                startActivity(intent);
                finish();

            }
        });
    }
}