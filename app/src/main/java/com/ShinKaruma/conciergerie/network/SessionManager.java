package com.ShinKaruma.conciergerie.network;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.ShinKaruma.conciergerie.pojo.User;

public class SessionManager {

    private static SessionManager instance;

    private static final String PREF_NAME = "auth_prefs";
    private static final String KEY_ACCESS = "access_token";
    private static final String KEY_REFRESH = "refresh_token";

    private User currentUser;

    private final SharedPreferences prefs;

    private SessionManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveAccessToken(String token) {
        prefs.edit().putString(KEY_ACCESS, token).apply();
    }

    public void saveRefreshToken(String token) {
        prefs.edit().putString(KEY_REFRESH, token).apply();
    }

    public String getAccessToken() {
        return prefs.getString(KEY_ACCESS, null);
    }

    public String getRefreshToken() {
        return prefs.getString(KEY_REFRESH, null);
    }

    public boolean hasValidAccessToken() {
        String token = getAccessToken();
        return token != null && !JwtUtils.isExpired(token);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
