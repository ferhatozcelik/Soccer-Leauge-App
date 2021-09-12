package com.ferhatozcelik.soccerleauge.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PointDao {

    @Query("SELECT * from point_table")
    LiveData<List<Point>> getAllPointItem();

    @Insert
    void insert(Point point);

    @Query("DELETE FROM point_table")
    void deleteAll();
}
