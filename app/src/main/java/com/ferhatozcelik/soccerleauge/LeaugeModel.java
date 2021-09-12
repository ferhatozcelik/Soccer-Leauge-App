package com.ferhatozcelik.soccerleauge;


public class LeaugeModel {

    private String key;
    private String league;

    public LeaugeModel(String key, String league) {
        this.key = key;
        this.league = league;
    }

    public String getKey() {
        return key;
    }

    public String getLeague() {
        return league;
    }

}