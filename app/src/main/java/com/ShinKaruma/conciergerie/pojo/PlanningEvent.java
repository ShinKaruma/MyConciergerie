package com.ShinKaruma.conciergerie.pojo;

public class PlanningEvent {

    private String type;

    private Location location;

    public PlanningEvent(String type, Location location) {
        this.type = type;
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
