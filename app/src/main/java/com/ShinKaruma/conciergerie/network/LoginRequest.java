package com.ShinKaruma.conciergerie.network;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
     @SerializedName("email")
     String email;

     @SerializedName("password")
     String password;


     public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

     String getEmail() {
        return email;
    }

     void setEmail(String email) {
        this.email = email;
    }

     String getPassword() {
        return password;
    }

     void setPassword(String password) {
        this.password = password;
    }
}
