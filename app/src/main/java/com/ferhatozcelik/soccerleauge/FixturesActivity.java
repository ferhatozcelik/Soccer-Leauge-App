package com.ferhatozcelik.soccerleauge;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ferhatozcelik.soccerleauge.adapter.FixturesAdapter;
import com.ferhatozcelik.soccerleauge.adapter.PointAdapter;
import com.ferhatozcelik.soccerleauge.database.Fixtures;
import com.ferhatozcelik.soccerleauge.database.Leauge;
import com.ferhatozcelik.soccerleauge.database.Point;
import com.ferhatozcelik.soccerleauge.network.DataServiceGenerator;
import com.ferhatozcelik.soccerleauge.network.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixturesActivity extends AppCompatActivity {

    private List<Fixtures> fixtureList;
    private FixturesViewModel fixtureViewModel;
    private ArrayList<Fixtures> fixtureDbList;

    private ViewPager viewPager;

    private FixturesAdapter fixturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        ViewModelInit();

    }

    private void ViewModelInit() {

        fixtureDbList = new ArrayList<>();
        fixtureViewModel = ViewModelProviders.of(this).get(FixturesViewModel.class);
        fixtureViewModel.getmAllFixtures().observe(this, new Observer<List<Fixtures>>() {
            @Override
            public void onChanged(@Nullable final List<Fixtures> fixtures) {
                fixtureList = fixtures;
            }
        });



        GetItem();
    }

    private void GetItem() {
        String currentleauge = getIntent().getExtras().getString("currentleauge");
        int currentweek = getIntent().getExtras().getInt("currentweek");
        Log.d("score:",currentweek+"");
        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        Service service = DataServiceGenerator.createService(Service.class);
        Call<List<FixturesModel>> call = service.getmAllFixtures(currentleauge, String.valueOf(currentweek));

        call.enqueue(new Callback<List<FixturesModel>>() {
            @Override
            public void onResponse(Call<List<FixturesModel>> call, Response<List<FixturesModel>> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        List<FixturesModel> fixtureModelList = response.body();

                        Log.d("score:",fixtureModelList.toString());
                        Log.d("score:","notnull");

                        for (int i = 0; i < fixtureModelList.size(); i++){
                            String week = fixtureModelList.get(i).getFixture_week();
                            String league = fixtureModelList.get(i).getFixture_league();
                            String score = fixtureModelList.get(i).getScore();
                            String date = fixtureModelList.get(i).getDate();
                            String awayLogo = fixtureModelList.get(i).getAwayLogo();
                            String homeLogo = fixtureModelList.get(i).getHomeLogo();
                            String away = fixtureModelList.get(i).getAway();
                            String home = fixtureModelList.get(i).getHome();

                            Log.d("score:",score);
                            Fixtures fixturesModel = new Fixtures(week,league,score,date,awayLogo,homeLogo,away,home);
                            fixtureViewModel.insert(fixturesModel);
                            fixtureDbList.add(fixturesModel);

                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                takeActionView();
                            }
                        }, 3000);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<FixturesModel>> call, Throwable t) {
                Log.d("MainActivityOnFailure:",t.getMessage());
            }
        });

    }

    private void takeActionView() {
        viewPager = findViewById(R.id.viewPager);
        fixturesAdapter = new FixturesAdapter(this, fixtureDbList);
        viewPager.setAdapter(fixturesAdapter);
        viewPager.setPadding(100,0,100,0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

}