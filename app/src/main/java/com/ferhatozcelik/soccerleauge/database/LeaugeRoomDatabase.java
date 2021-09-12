package com.ferhatozcelik.soccerleauge.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Leauge.class}, version = 1)
public abstract class LeaugeRoomDatabase extends RoomDatabase {

    public abstract LeaugeDao leaugeDao();

    private static LeaugeRoomDatabase INSTANCE;

    static LeaugeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LeaugeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LeaugeRoomDatabase.class, "league")
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
