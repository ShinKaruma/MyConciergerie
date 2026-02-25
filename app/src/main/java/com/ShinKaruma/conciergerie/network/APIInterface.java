package com.ShinKaruma.conciergerie.network;


import com.ShinKaruma.conciergerie.adders.AppartementCreateDTO;
import com.ShinKaruma.conciergerie.pojo.Appartement;
import com.ShinKaruma.conciergerie.pojo.CalendarEvent;
import com.ShinKaruma.conciergerie.pojo.Concierge;
import com.ShinKaruma.conciergerie.pojo.PlanningDay;
import com.ShinKaruma.conciergerie.pojo.Proprietaire;
import com.ShinKaruma.conciergerie.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface {

    @GET("me")
    Call<User> getMe();

    @GET("proprietaires")
    Call<List<Proprietaire>> getProprietaires(
    );

    @GET("proprietaires")
    Call<List<Proprietaire>> getProprietairesByUserId(
            @Query("user") int idUser
    );

    @GET("concierges")
    Call<List<Concierge>> getConcierges(
    );

    @GET("concierge")
    Call<List<Concierge>> getConciergeByUserId(
            @Query("user") int idUser
    );

    @POST("appartements")
    Call<Void> createAppartement(
        @Body AppartementCreateDTO appartement
    );

    @GET("appartements")
    Call<List<Appartement>> getAppartements(
    );

    @GET("appartements/{idAppartement}")
    Call<Appartement> getAppartementById(
            @Path("idAppartement") int idAppartement
    );

    @GET("planning")
    Call<List<PlanningDay>> getPlanning();

    @GET("calendar")
    Call<List<CalendarEvent>> getCalendar(
            @Query("from") String from,
            @Query("to") String to
            );






}
