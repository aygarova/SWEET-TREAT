package com.example.shopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopapp.R;
import com.example.shopapp.models.SingInModel;
import com.example.shopapp.validation.UserValidator;

public class SingIn extends AppCompatActivity {

    private EditText password;
    private EditText email;
    private TextView register;

    private UserValidator userValidator = new UserValidator();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in2);

        getSupportActionBar().setTitle("Вход");

        Toast.makeText(SingIn.this, "Влезте в профила си", Toast.LENGTH_SHORT).show();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.sing_up);

        Button button = findViewById(R.id.singIn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                SingInModel singInModel = new SingInModel(emailValue, passwordValue);
                if (userValidator.isUserLogInValid(singInModel, SingIn.this)) {
                    Intent intent = new Intent(SingIn.this, MyProfile.class);
                    startActivity(intent);
                }

            }
        });

        register.setOnClickListener(view -> startActivities(new Intent[]{new Intent(SingIn.this, Register.class)}));
    }
}