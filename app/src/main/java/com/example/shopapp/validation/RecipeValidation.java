package com.example.shopapp.validation;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;



import com.example.shopapp.Activity.Recipe;

import com.example.shopapp.models.RecipeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeValidation {

    public boolean checkForCorrectField(String titleValue, String  descriptionValue, String servingsValue, List<String> ingredientsValue, Recipe recipe) {
        boolean flag = true;
        String error = "";

        if (TextUtils.isEmpty(titleValue)){
            error += "Please enter Recipe tittle\n";
            flag = false;
        }

        if (TextUtils.isEmpty(descriptionValue)){
            error += "Please enter Recipe description\n";
            flag = false;
        }

        if (TextUtils.isEmpty(servingsValue)){
            error += "Please enter Recipe servings\n";
            flag = false;
        }

         if (ingredientsValue.isEmpty()) {
            error += "Please enter ingredients split ingredients with \", \"\n";
            flag = false;
        }

        if (flag) {
            return true;
        } else {
            Toast toast = Toast.makeText(recipe.getBaseContext(), error, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        return false;
    }

    public RecipeModel createRecipe(EditText title, EditText description, EditText servings, EditText ingredients, Recipe recipe) {
        String titleValue = title.getText().toString();
        String descriptionValue = description.getText().toString();
        String servingsValues = servings.getText().toString();
        List<String> ingredientsValue = new ArrayList<>(Arrays.asList(ingredients.getText().toString().split(", ")));
        if (checkForCorrectField(titleValue, descriptionValue, servingsValues, ingredientsValue, recipe)) {
            return new RecipeModel(titleValue, descriptionValue, servingsValues, ingredientsValue);
        }
        return null;
    }

}
