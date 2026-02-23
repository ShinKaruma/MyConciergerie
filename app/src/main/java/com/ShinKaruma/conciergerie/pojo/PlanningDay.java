package com.ShinKaruma.conciergerie.pojo;

import java.util.List;

public class PlanningDay {
    private String date;
    private List<PlanningEvent> events;

    public PlanningDay(String date, List<PlanningEvent> events){
        this.date = date;
        this.events = events;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<PlanningEvent> getEvents() {
        return events;
    }

    public void setEvents(List<PlanningEvent> events) {
        this.events = events;
    }
}
