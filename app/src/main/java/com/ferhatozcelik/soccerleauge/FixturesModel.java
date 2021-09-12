package com.ferhatozcelik.soccerleauge;


import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FixturesModel {


    private String fixture_week;
    private String fixture_league;
    private String score;
    private String date;
    private String awayLogo;
    private String homeLogo;
    private String away;
    private String home;

    public FixturesModel(String fixture_week, String fixture_league, String score, String date, String awayLogo, String homeLogo, String away, String home) {
        this.fixture_week = fixture_week;
        this.fixture_league = fixture_league;
        this.score = score;
        this.date = date;
        this.awayLogo = awayLogo;
        this.homeLogo = homeLogo;
        this.away = away;
        this.home = home;
    }

    public String getFixture_week() {
        return fixture_week;
    }

    public String getFixture_league() {
        return fixture_league;
    }

    public String getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getAwayLogo() {
        return awayLogo;
    }

    public String getHomeLogo() {
        return homeLogo;
    }

    public String getAway() {
        return away;
    }

    public String getHome() {
        return home;
    }
}