package com.ShinKaruma.conciergerie.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

    private static final String BASE_URL = "http://192.168.3.24:8000/api/";

    private static Retrofit retrofit;

    public static APIInterface createApi(Context context) {

        SessionManager sessionManager = SessionManager.getInstance(context);
        TokenRefreshService tokenRefreshService = new TokenRefreshService(AuthApiClient.getInstance());

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        AuthInterceptor interceptor = new AuthInterceptor(sessionManager, tokenRefreshService);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .addInterceptor(logging)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.24:8000/api/")

                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(APIInterface.class);
    }

}
