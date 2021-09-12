package com.ferhatozcelik.soccerleauge.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FixturesDao {

    @Query("SELECT * from fixture_table")
    LiveData<List<Fixtures>> getAllFixtures();

    @Insert
    void insert(Fixtures fixtures);

    @Query("DELETE FROM fixture_table")
    void deleteAll();
}
