package com.ShinKaruma.conciergerie.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthApiClient {

    private static final String BASE_URL = "http://192.168.3.24:8000/api/";

    private static Retrofit retrofit;

    private void AuthApiClient() {
    }

    public static AuthApi getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder().build()) // SANS interceptor
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AuthApi.class);
    }
}
