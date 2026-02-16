package com.ShinKaruma.conciergerie.pojo;

import com.google.gson.annotations.SerializedName;
public class Appartement {
    @SerializedName("id")
    private int id = 0;
    private Proprietaire proprietaire;
    private String nom;
    private String lieu;
    private String numero;
    private String codeCle;
    private String codePorte;
    private int nbKitsDispo = 0;
    private boolean status;

    public Appartement(int id, Proprietaire proprietaire, String nom, String lieu, String numero, String codeCle, String codePorte, int nbKitsDispo, boolean status) {
        this.id = id;
        this.proprietaire = proprietaire;
        this.nom = nom;
        this.lieu = lieu;
        this.numero = numero;
        this.codeCle = codeCle;
        this.codePorte = codePorte;
        this.nbKitsDispo = nbKitsDispo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodeCle() {
        return codeCle;
    }

    public void setCodeCle(String codeCle) {
        this.codeCle = codeCle;
    }

    public String getCodePorte() {
        return codePorte;
    }

    public void setCodePorte(String codePorte) {
        this.codePorte = codePorte;
    }

    public int getNbKitsDispo() {
        return nbKitsDispo;
    }

    public void setNbKitsDispo(int nbKitsDispo) {
        this.nbKitsDispo = nbKitsDispo;
    }

    public boolean isEstOccupe() {
        return status;
    }
}
