package com.ShinKaruma.conciergerie.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Location {
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private Appartement appartement;
    private Locataire locataire;
    private List<LocationService> services;
    @SerializedName("active")
    private boolean isActive;


    public Location(int id, Date dateDebut, Date dateFin, Appartement appartement, Locataire locataire, List<LocationService> services, boolean isActive) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.appartement = appartement;
        this.locataire = locataire;
        this.services = services;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Locataire getLocataire() {
        return locataire;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }

    public Appartement getAppartement() {
        return appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
    }

    public List<LocationService> getServices() {
        return services;
    }

    public void setServices(List<LocationService> services) {
        this.services = services;
    }

    public boolean isActive() {
        return isActive;
    }

}
