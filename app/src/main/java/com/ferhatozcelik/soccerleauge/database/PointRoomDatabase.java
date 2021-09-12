package com.ferhatozcelik.soccerleauge.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Point.class}, version = 2)
public abstract class PointRoomDatabase extends RoomDatabase {

    public abstract PointDao pointDao();

    private static PointRoomDatabase INSTANCE;

    static PointRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PointRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PointRoomDatabase.class, "point")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };


}
