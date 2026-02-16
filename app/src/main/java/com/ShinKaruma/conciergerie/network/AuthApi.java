package com.ShinKaruma.conciergerie.network;

import com.ShinKaruma.conciergerie.pojo.Proprietaire;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("login_check")
    Call<TokenPair> login(@Body LoginRequest login);

    @POST("token/refresh/")
    Call<TokenPair> refresh(@Body RefreshRequest request);

    @POST("register")
    Call<ConciergeRegister> registerConcierge(@Body ConciergeRegister user);

    @POST("register")
    Call<ProprietaireRegister> registerProprietaire(@Body ProprietaireRegister user);


}
