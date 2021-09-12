package com.ferhatozcelik.soccerleauge.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FixtureRepository {

    private FixturesDao mFixturesDao;
    private LiveData<List<Fixtures>> mAllFixtures;


    public FixtureRepository(Application application) {
        FixturesRoomDatabase db = FixturesRoomDatabase.getDatabase(application);
        mFixturesDao = db.fixturesDao();
        mAllFixtures = mFixturesDao.getAllFixtures();
    }

    public  LiveData<List<Fixtures>> getmAllFixtures() {
        return mAllFixtures;
    }

    public void insert (Fixtures fixtures) {
        new insertAsyncTask(mFixturesDao).execute(fixtures);
    }
    private static class insertAsyncTask extends AsyncTask<Fixtures, Void, Void> {

        private FixturesDao mAsyncTaskDao;

        insertAsyncTask(FixturesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Fixtures... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
