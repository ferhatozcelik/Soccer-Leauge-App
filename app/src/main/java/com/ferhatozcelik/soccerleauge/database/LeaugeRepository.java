package com.ferhatozcelik.soccerleauge.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class LeaugeRepository {

    private LeaugeDao mLeaugeDao;
    private LiveData<List<Leauge>> mAllLeauge;

    public LeaugeRepository(Application application) {
        LeaugeRoomDatabase db = LeaugeRoomDatabase.getDatabase(application);
        mLeaugeDao = db.leaugeDao();
        mAllLeauge = mLeaugeDao.getAllLeauge();
    }

    public  LiveData<List<Leauge>> getmAllLeauge() {
        return mAllLeauge;
    }

    public void insert (Leauge leauge) {
        new insertAsyncTask(mLeaugeDao).execute(leauge);
    }

    private static class insertAsyncTask extends AsyncTask<Leauge, Void, Void> {

        private LeaugeDao mAsyncTaskDao;

        insertAsyncTask(LeaugeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Leauge... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
