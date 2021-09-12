package com.ferhatozcelik.soccerleauge.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Ferhat OZCELIK
 */

@Entity(tableName = "league_table")
public class Leauge {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name = "league")
    private String league;

    @Nullable
    @ColumnInfo(name = "key")
    private String key;


    public Leauge(@NonNull String league, @Nullable String key) {
        this.league = league;
        this.key = key;
    }

    @NonNull
    public String getLeague() {
        return league;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return league;
    }
}


