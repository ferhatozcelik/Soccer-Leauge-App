package com.ferhatozcelik.soccerleauge;


import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PointModel {

    public PointModel(String points_week, String points_league, String teamname, String play, String win, String lose, String tie, String average, String point) {
        this.points_week = points_week;
        this.points_league = points_league;
        this.teamname = teamname;
        this.play = play;
        this.win = win;
        this.lose = lose;
        this.tie = tie;
        this.average = average;
        this.point = point;
    }

    private String points_week;
    private String points_league;
    private String teamname;
    private String play;
    private String win;
    private String lose;
    private String tie;
    private String average;
    private String point;

    public String getWeek() {
        return points_week;
    }

    public String getLeague() {
        return points_league;
    }

    public String getTeamname() {
        return teamname;
    }

    public String getPlay() {
        return play;
    }

    public String getWin() {
        return win;
    }

    public String getLose() {
        return lose;
    }

    public String getTie() {
        return tie;
    }

    public String getAverage() {
        return average;
    }

    public String getPoint() {
        return point;
    }
}