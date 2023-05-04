package com.example.shopapp.repository;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.Adapter.MyAdapters;
import com.example.shopapp.Adapter.RecipeAdapter;
import com.example.shopapp.models.RecipeModel;
import com.example.shopapp.models.Users;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private boolean isEmptyRecipeCatalog = false;


    public boolean allRecipeOfUser(MyAdapters adapter, List<RecipeModel> myObjectList) {
        Query query = firestore.collection("recipes").whereEqualTo("userKey", firebaseAuth.getCurrentUser().getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot.isEmpty()) {
                    isEmptyRecipeCatalog = true;
                }

                myObjectList.clear();

                for (DocumentSnapshot document : snapshot.getDocuments()) {
                    RecipeModel myObject = document.toObject(RecipeModel.class);
                    myObjectList.add(myObject);
                }

                adapter.notifyDataSetChanged();
            }
        });
        return isEmptyRecipeCatalog;
    }

    public void setUpRecipeAdapter (RecyclerView recyclerView) {
        CollectionReference recipesRef = firestore.collection("recipes");
        Query query = recipesRef.whereEqualTo("userId", firebaseAuth.getCurrentUser().getUid());

        FirestoreRecyclerOptions<RecipeModel> options = new FirestoreRecyclerOptions.Builder<RecipeModel>()
                .setQuery(query, RecipeModel.class)
                .build();

        RecipeAdapter adapter = new RecipeAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    public Task<DocumentReference>  addRecipeToDatabase(RecipeModel recipeModel) {
        CollectionReference recipes = firestore.collection("recipes");
        Map<String, Object> recipeData = new HashMap<>();
        recipeData.put("title", recipeModel.getTitle());
        recipeData.put("description", recipeModel.getDescription());
        recipeData.put("servings", recipeModel.getServings());
        recipeData.put("ingredients", recipeModel.getIngredients());
        recipeData.put("userKey", firebaseAuth.getCurrentUser().getUid());

        return recipes.add(recipeData);
    }

    public Task<AuthResult> createUser (Users users) {
        return firebaseAuth.createUserWithEmailAndPassword(users.getEmail(), users.getPassword());
    }

    public Task<Void> registerUser(Users users) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", users.getName());
        user.put("email", users.getEmail());
        return firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .set(user);
    }

    public Task<AuthResult> loginUser(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }
}
