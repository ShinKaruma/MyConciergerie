package com.ShinKaruma.conciergerie.pojo;

public class LocationService {
    private int id;
    private Service service;
    private int quantite;

    public LocationService(int id, Service service, int quantite) {
        this.id = id;
        this.service = service;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
