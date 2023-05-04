package com.example.shopapp.validation;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopapp.Activity.Register;
import com.example.shopapp.Activity.SingIn;
import com.example.shopapp.models.SignUpModel;
import com.example.shopapp.models.SingInModel;
import com.example.shopapp.models.Users;
import com.example.shopapp.repository.Database;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends AppCompatActivity {
    private boolean signInSuccessful;
    private Database database = new Database();


    public UserValidator() {
    }

    public boolean isPasswordStrong(String password) {
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$";
        Pattern pattern = Pattern.compile(regexPassword);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

        public static boolean isValidEmail(String email) {
        String regexEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(regexEmail);

        Matcher matcher = pattern.matcher(email.toLowerCase(Locale.ROOT));

        return matcher.matches();
    }

    public boolean isUserValid(SignUpModel signUpModel, Register register) {
        boolean flag = true;
        String error = "";
        if (TextUtils.isEmpty(signUpModel.getName())) {
            error += "Моля, въведете име\n";
            flag = false;
        }

        if (TextUtils.isEmpty(signUpModel.getEmail())) {
            error += "Моля, въведете имейл\n";
            flag = false;
        } else if (!isValidEmail(signUpModel.getEmail())) {
            error += "Моля, въведете валиден имейл\n";
            flag = false;
        }

        if (TextUtils.isEmpty(signUpModel.getPassword())) {
            error += "Моля, въведете парола\n";
            flag = false;
        } else if (isPasswordStrong(signUpModel.getPassword())) {
            error += "(Паролата трябва да съдържа малки и големи букви, цифри и поне един символ" + //
                    "\nдължина между 6 и 20 сумвола)\n";
            flag = false;

        }

        if (TextUtils.isEmpty(signUpModel.getRePassword())) {
            error += "Моля, повторете паролата\n";
            flag = false;

        } else if (!isPasswordMatch(signUpModel.getPassword(), signUpModel.getRePassword())) {
            error += "Паролата трябва да съвпада";
            flag = false;

        }

        if (flag) {
            Users user = new Users(signUpModel.getName(),signUpModel.getEmail(), signUpModel.getPassword());
            registerUser(user, register);
            return signInSuccessful;
        } else {
            Toast toast = Toast.makeText(register.getBaseContext(), error, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return false;
    }


    public boolean isUserLogInValid(SingInModel singInModel, SingIn singIn) {
        boolean flag = true;
        String error = "";
        if (TextUtils.isEmpty(singInModel.getEmail())) {
            error += "Моля, въведете имейл\n";
            flag = false;
        } else if (!isValidEmail(singInModel.getEmail())) {
            error += "Моля, въведете валиден имейл\n";
            flag = false;
        }

        if (TextUtils.isEmpty(singInModel.getPassword())) {
            error += "Моля, въведете парола\n";
            flag = false;
        } else if (isPasswordStrong(singInModel.getPassword())) {
            error += "(Паролата трябва да съдържа малки и големи букви, цифри и поне един символ" + //
                    "\nдължина между 6 и 20 сумвола)";
            flag = false;

        }

        if (flag) {
            loginUser(singInModel.getEmail(), singInModel.getPassword(), singIn);
            return signInSuccessful;
        } else {
            Toast toast = Toast.makeText(singIn.getBaseContext(), error, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
    }

    public void registerUser(Users users, Register register) {
        database.createUser(users).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        database.registerUser(users)
                                .addOnSuccessListener(documentReference -> {
                                    signInSuccessful = true;
                                    Toast.makeText(register.getBaseContext(), "Успешна регистрация", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        signInSuccessful = false;
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthUserCollisionException e) {
                            Toast.makeText(register.getBaseContext(), "Този имейл вече е използван", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(register.getBaseContext(), "Проблем при регистрация" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void loginUser(String email, String password, SingIn signIn) {
        database.loginUser(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(signIn.getBaseContext(), "Успешен вход", Toast.LENGTH_SHORT).show();
                signInSuccessful = true;
            } else {
                Toast.makeText(signIn.getBaseContext(), "Грешен имейл или парола", Toast.LENGTH_SHORT).show();
                signInSuccessful = false;
            }
        });
    }

    private boolean isPasswordMatch(String password, String rePassword) {
        return password.equals(rePassword);
    }
}
