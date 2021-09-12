package com.ferhatozcelik.soccerleauge.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "point_table")
public class Point {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    public Point(@NonNull String week, @NonNull String league, @NonNull String teamname, @Nullable String play, @Nullable String win, @Nullable String lose, @Nullable String tie, @Nullable String average, @Nullable String point) {
        this.week = week;
        this.league = league;
        this.teamname = teamname;
        this.play = play;
        this.win = win;
        this.lose = lose;
        this.tie = tie;
        this.average = average;
        this.point = point;
    }

    @NonNull
    @ColumnInfo(name = "week")
    private String week;

    @NonNull
    @ColumnInfo(name = "league")
    private String league;

    @NonNull
    @ColumnInfo(name = "teamname")
    private String teamname;

    @Nullable
    @ColumnInfo(name = "play")
    private String play;

    @Nullable
    @ColumnInfo(name = "win")
    private String win;

    @Nullable
    @ColumnInfo(name = "lose")
    private String lose;

    @Nullable
    @ColumnInfo(name = "tie")
    private String tie;

    @Nullable
    @ColumnInfo(name = "average")
    private String average;

    @Nullable
    @ColumnInfo(name = "point")
    private String point;

    @NonNull
    public String getWeek() {
        return week;
    }

    @NonNull
    public String getLeague() {
        return league;
    }

    @NonNull
    public String getTeamname() {
        return teamname;
    }

    @Nullable
    public String getPlay() {
        return play;
    }

    @Nullable
    public String getWin() {
        return win;
    }

    @Nullable
    public String getLose() {
        return lose;
    }

    @Nullable
    public String getTie() {
        return tie;
    }

    @Nullable
    public String getAverage() {
        return average;
    }

    @Nullable
    public String getPoint() {
        return point;
    }
}
