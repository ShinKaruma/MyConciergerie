package com.ShinKaruma.conciergerie.pojo;

import com.google.gson.annotations.SerializedName;

public class Proprietaire{
    @SerializedName("id")
    private int proprietaireId;
    private String couleur;

    private User user;

    public Proprietaire(int proprietaireId, User pUser, String couleur){
        user = pUser;
        this.proprietaireId = proprietaireId;
        this.couleur = couleur;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getProprietaireId() {
        return proprietaireId;
    }

    public void setProprietaireId(int proprietaireId) {
        this.proprietaireId = proprietaireId;
    }

    public User getUser() {
        return user;
    }


    @Override
    public String toString(){
        return user.getNom() + " " + user.getPrenom();
    }


}
