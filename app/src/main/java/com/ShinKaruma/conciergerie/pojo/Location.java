package com.ShinKaruma.conciergerie.pojo;

import java.util.Date;
import java.util.List;

public class Location {
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private int appartementId;
    private int locataireId;
    private List<LocationService> services;

    public Location(int id, Date dateDebut, Date dateFin, int appartementId, int locataireId, List<LocationService> services) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.appartementId = appartementId;
        this.locataireId = locataireId;
        this.services = services;
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

    public int getAppartementId() {
        return appartementId;
    }

    public void setAppartementId(int appartementId) {
        this.appartementId = appartementId;
    }

    public int getLocataireId() {
        return locataireId;
    }

    public void setLocataireId(int locataireId) {
        this.locataireId = locataireId;
    }

    public List<LocationService> getServices() {
        return services;
    }

    public void setServices(List<LocationService> services) {
        this.services = services;
    }
}
