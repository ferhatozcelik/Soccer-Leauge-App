package com.ferhatozcelik.soccerleauge;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ferhatozcelik.soccerleauge.database.FixtureRepository;
import com.ferhatozcelik.soccerleauge.database.Fixtures;
import com.ferhatozcelik.soccerleauge.database.Leauge;
import com.ferhatozcelik.soccerleauge.database.LeaugeRepository;

import java.util.List;


public class FixturesViewModel extends AndroidViewModel {

    private FixtureRepository mRepository;
    private LiveData<List<Fixtures>> mAllFixtures;

    public FixturesViewModel(Application application) {
        super(application);
        mRepository = new FixtureRepository(application);
        mAllFixtures = mRepository.getmAllFixtures();
    }



    LiveData<List<Fixtures>> getmAllFixtures() { return mAllFixtures; }

    public void insert(Fixtures fixtures) {
        mRepository.insert(fixtures); }
}