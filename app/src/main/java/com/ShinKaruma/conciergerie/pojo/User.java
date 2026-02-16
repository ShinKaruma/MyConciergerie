package com.ShinKaruma.conciergerie.pojo;

public class User {
    private Integer id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private String typeUser;
    private Conciergerie conciergerie;


    public User(Integer id, String email, String password, String nom, String prenom,String type, String telephone, Conciergerie conciergerie) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.typeUser = type;
        this.conciergerie = conciergerie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getType() {
        return typeUser;
    }

    public void setType(String type) {
        this.typeUser = type;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Conciergerie getConciergerie() {
        return conciergerie;
    }

    public void setConciergerie(Conciergerie conciergerie) {
        this.conciergerie = conciergerie;
    }
}
