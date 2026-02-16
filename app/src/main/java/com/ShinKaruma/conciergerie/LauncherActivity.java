package com.ShinKaruma.conciergerie;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ShinKaruma.conciergerie.network.SessionManager;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isLoggedIn = checkUserSession();

        Intent intent = new Intent(this, isLoggedIn ? MainActivity.class : AuthActivity.class);
        startActivity(intent);
        finish();

    }

    private boolean checkUserSession(){
        SessionManager sessionManager = SessionManager.getInstance(this);
        return sessionManager.hasValidAccessToken();
    }

}