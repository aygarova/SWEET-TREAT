package com.example.shopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopapp.Adapter.MyAdapters;
import com.example.shopapp.R;
import com.example.shopapp.models.RecipeModel;
import com.example.shopapp.repository.Database;
import java.util.ArrayList;
import java.util.List;

public class RecipeCatalog extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MyAdapters adapter;
    private List<RecipeModel> myObjectList;
    private Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myObjectList = new ArrayList<>();
        adapter = new MyAdapters(myObjectList);
        recyclerView.setAdapter(adapter);

        Button myProfileButton = findViewById(R.id.button);
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeCatalog.this, MyProfile.class);
                startActivity(intent);
                finish();
            }
        });
        database.allRecipeOfUser(adapter, myObjectList);
    }
}
