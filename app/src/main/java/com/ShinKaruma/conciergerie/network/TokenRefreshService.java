package com.ShinKaruma.conciergerie.network;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class TokenRefreshService {

    private final AuthApi api;

    public TokenRefreshService(AuthApi api) {
        this.api = api;
    }

    public TokenPair refresh(String refreshToken) {
        try {
            Response<TokenPair> response =
                    api.refresh(new RefreshRequest(refreshToken)).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (IOException ignored) {}

        return null;
    }
}
