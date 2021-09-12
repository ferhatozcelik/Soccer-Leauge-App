package com.ferhatozcelik.soccerleauge;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ferhatozcelik.soccerleauge.database.Leauge;
import com.ferhatozcelik.soccerleauge.database.LeaugeRepository;
import com.ferhatozcelik.soccerleauge.database.Point;
import com.ferhatozcelik.soccerleauge.database.PointRepository;

import java.util.List;

public class PointViewModel extends AndroidViewModel {

    private PointRepository mRepository;
    private LiveData<List<Point>> mAllPoint;

    public PointViewModel(Application application) {
        super(application);
        mRepository = new PointRepository(application);
        mAllPoint = mRepository.getmAllPoint();
    }

    LiveData<List<Point>> getAllPoint() { return mAllPoint; }

    public void insert(Point point) {
        mRepository.insert(point); }
}