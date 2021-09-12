package com.ferhatozcelik.soccerleauge.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
/**
 * Created by Ferhat OZCELIK
 */

@Entity(tableName = "fixture_table")
public class Fixtures {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "fixture_week")
    private String fixture_week;

    @NonNull
    @ColumnInfo(name = "fixture_league")
    private String fixture_league;

    @NonNull
    @ColumnInfo(name = "score")
    private String score;

    @Nullable
    @ColumnInfo(name = "date")
    private String date;

    @Nullable
    @ColumnInfo(name = "awayLogo")
    private String awayLogo;

    @Nullable
    @ColumnInfo(name = "homeLogo")
    private String homeLogo;

    @Nullable
    @ColumnInfo(name = "away")
    private String away;

    @Nullable
    @ColumnInfo(name = "home")
    private String home;

    public Fixtures(@NonNull String fixture_week, @NonNull String fixture_league, @NonNull String score, @Nullable String date, @Nullable String awayLogo, @Nullable String homeLogo, @Nullable String away, @Nullable String home) {
        this.fixture_week = fixture_week;
        this.fixture_league = fixture_league;
        this.score = score;
        this.date = date;
        this.awayLogo = awayLogo;
        this.homeLogo = homeLogo;
        this.away = away;
        this.home = home;
    }

    @NonNull
    public String getFixture_week() {
        return fixture_week;
    }

    @NonNull
    public String getFixture_league() {
        return fixture_league;
    }

    @NonNull
    public String getScore() {
        return score;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    @Nullable
    public String getAwayLogo() {
        return awayLogo;
    }

    @Nullable
    public String getHomeLogo() {
        return homeLogo;
    }

    @Nullable
    public String getAway() {
        return away;
    }

    @Nullable
    public String getHome() {
        return home;
    }
}


