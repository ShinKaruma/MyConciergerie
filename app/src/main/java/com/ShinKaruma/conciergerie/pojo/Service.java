package com.ShinKaruma.conciergerie.pojo;

public class Service {
    private int id;
    private String nom;
    private double duree;
    private double prix;

    public Service(int id, String nom, double duree, double prix) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
