package com.ShinKaruma.conciergerie.pojo;

import java.io.Serializable;
import java.time.LocalDate;

public class CalendarEvent implements Serializable {
    public int id;
    public String start;
    public String end;
    public String appartementNom;
    public String proprietaireString;
    public String proprietaireColor;

}
