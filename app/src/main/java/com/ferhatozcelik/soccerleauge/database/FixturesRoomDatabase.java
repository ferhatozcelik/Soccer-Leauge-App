package com.ferhatozcelik.soccerleauge.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Fixtures.class}, version = 2)
public abstract class FixturesRoomDatabase extends RoomDatabase {

    public abstract FixturesDao fixturesDao();

    private static FixturesRoomDatabase INSTANCE;

    static FixturesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FixturesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FixturesRoomDatabase.class, "fixtures")
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
