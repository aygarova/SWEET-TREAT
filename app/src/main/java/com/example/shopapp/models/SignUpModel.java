package com.example.shopapp.models;

public class SignUpModel {

    private String name;
    private String email;
    private String password;
    private String rePassword;

    public SignUpModel(String name, String email, String password, String rePassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
