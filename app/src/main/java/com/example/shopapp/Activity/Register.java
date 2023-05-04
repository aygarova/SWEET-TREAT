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
import com.example.shopapp.models.SignUpModel;

import com.example.shopapp.validation.UserValidator;

public class Register extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private EditText email;
    private EditText rePassword;
    private UserValidator userValidator = new UserValidator();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        getSupportActionBar().setTitle("Регистрация");

        Toast.makeText(Register.this, "Може да се регистрирате сега", Toast.LENGTH_SHORT).show();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);

        Button button = findViewById(R.id.singIn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue = name.getText().toString();
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String rePasswordValue = rePassword.getText().toString();

                SignUpModel signUpModel = new SignUpModel(nameValue, emailValue, passwordValue, rePasswordValue);

                if (userValidator.isUserValid(signUpModel, Register.this)){
                    Intent intent = new Intent(Register.this, SingIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}