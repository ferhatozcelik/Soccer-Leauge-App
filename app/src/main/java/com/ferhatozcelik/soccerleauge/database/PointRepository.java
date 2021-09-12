package com.ferhatozcelik.soccerleauge.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PointRepository {

    private PointDao mPointDao;
    private LiveData<List<Point>> mAllPoint;


    public PointRepository(Application application) {
        PointRoomDatabase db = PointRoomDatabase.getDatabase(application);
        mPointDao = db.pointDao();
        mAllPoint= mPointDao.getAllPointItem();
    }

    public  LiveData<List<Point>> getmAllPoint() {
        return mAllPoint;
    }

    public void insert (Point point) {
        new insertAsyncTask(mPointDao).execute(point);
    }

    private static class insertAsyncTask extends AsyncTask<Point, Void, Void> {

        private PointDao mAsyncTaskDao;

        insertAsyncTask(PointDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Point... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
