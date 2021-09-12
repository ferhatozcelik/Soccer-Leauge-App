package com.ferhatozcelik.soccerleauge.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LeaugeDao {

    @Query("SELECT * from league_table")
    LiveData<List<Leauge>> getAllLeauge();

    @Insert
    void insert(Leauge leauge);

    @Query("DELETE FROM league_table")
    void deleteAll();
}
