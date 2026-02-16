package com.ShinKaruma.conciergerie.network;

public class ProprietaireRegister {
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private String codeSynchro;
    private String couleur;

    public ProprietaireRegister(String email, String password, String nom, String prenom, String telephone, String codeSynchro, String couleur) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.codeSynchro = codeSynchro;
        this.couleur = couleur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCodeSynchro() {
        return codeSynchro;
    }

    public void setCodeSynchro(String codeSynchro) {
        this.codeSynchro = codeSynchro;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
