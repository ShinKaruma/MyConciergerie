package com.ShinKaruma.conciergerie.pojo;

public class Concierge{
    private int conciergeId;

    private User user;
    public Concierge(int conciergeId, User user) {
        this.user = user;
        this.conciergeId = conciergeId;
    }

    public int getConciergeId() {
        return conciergeId;
    }

    public void setConciergeId(int conciergeId) {
        this.conciergeId = conciergeId;
    }
}
