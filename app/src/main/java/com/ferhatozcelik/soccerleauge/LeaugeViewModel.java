package com.ferhatozcelik.soccerleauge;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.ferhatozcelik.soccerleauge.database.Leauge;
import com.ferhatozcelik.soccerleauge.database.LeaugeRepository;
import com.ferhatozcelik.soccerleauge.database.Point;
import com.ferhatozcelik.soccerleauge.database.PointRepository;

import java.util.List;


public class LeaugeViewModel extends AndroidViewModel {

    private LeaugeRepository mRepository;
    private LiveData<List<Leauge>> mAllLeauge;

    public LeaugeViewModel(Application application) {
        super(application);
        mRepository = new LeaugeRepository(application);
        mAllLeauge = mRepository.getmAllLeauge();
    }



    LiveData<List<Leauge>> getAllLeauge() { return mAllLeauge; }

    public void insert(Leauge leauge) {
        mRepository.insert(leauge); }
}