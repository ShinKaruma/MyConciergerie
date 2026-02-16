package com.ShinKaruma.conciergerie.adders;

import com.ShinKaruma.conciergerie.pojo.Proprietaire;

public class AppartementCreateDTO {

    private String proprietaire;
    private String nom;
    private String lieu;
    private String numero;
    private String codeCle;
    private String codePorte;
    private int nbKitsDispo = 0;

    public AppartementCreateDTO(Proprietaire proprietaireId, String nom, String lieu, String numero, String codeCle, String codePorte, int nbKitsDispo) {
        this.proprietaire = "/api/proprietaires/" + proprietaireId.getProprietaireId();
        this.nom = nom;
        this.lieu = lieu;
        this.numero = numero;
        this.codeCle = codeCle;
        this.codePorte = codePorte;
        this.nbKitsDispo = nbKitsDispo;
    }

    public String getProprietaireId() {
        return proprietaire;
    }

    public void setProprietaireId(Proprietaire proprietaireId) {
        this.proprietaire = "/api/proprietaires/"+ proprietaireId.getProprietaireId();
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
}
