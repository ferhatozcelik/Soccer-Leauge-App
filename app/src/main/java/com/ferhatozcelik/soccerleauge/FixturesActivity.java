package com.ferhatozcelik.soccerleauge;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
/**
 * Created by Ferhat OZCELIK
 */
public class FixturesActivity extends AppCompatActivity {

    private List<Fixtures> fixtureList;
    private FixturesViewModel fixtureViewModel;
    ArrayList<Fixtures> fixtureDbList;
    private ArrayList<Fixtures> teamfixtureDbList;

    private ViewPager viewPager;

    FixturesAdapter fixturesAdapter;



    private ProgressDialog progressDialog;
    String currentteamName,currentleauge,currentleaugeName;
    int currentweek;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        TextView textViewWeek = findViewById(R.id.fixtureWeekText);
        TextView selectleagueText = findViewById(R.id.selectleagueText);



        fixtureDbList = new ArrayList<>();
        fixtureViewModel = ViewModelProviders.of(this).get(FixturesViewModel.class);
        fixtureViewModel.getmAllFixtures().observe(this, new Observer<List<Fixtures>>() {
            @Override
            public void onChanged(@Nullable final List<Fixtures> fixtures) {
                fixtureList = fixtures;
            }
        });

        currentteamName = getIntent().getExtras().getString("teamName");
        currentleauge = getIntent().getExtras().getString("currentleauge");
        currentleaugeName = getIntent().getExtras().getString("currentleaugeName");
        currentweek = getIntent().getExtras().getInt("currentweek");

        textViewWeek.setText(currentweek + ".Week");

        String type = getIntent().getExtras().getString("fixturestype");
        if (type.equals("teamfixture")){

            String[] ligsplit = currentleaugeName.split("-");


            selectleagueText.setText(ligsplit[0].substring(0, 1).toUpperCase() + ligsplit[0].substring(1) + " " +  ligsplit[1].substring(0, 1).toUpperCase() +  ligsplit[1].substring(1));


            GetTeamFixtureItem();
        }else if (type.equals("globalfixture")){
            selectleagueText.setText(currentleaugeName);
            GetGlobalFixtureItem();
        }

    }

    private void GetTeamFixtureItem() {

        ProgressDialogShow("Team Fixtures Loading...");
        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        Service service = DataServiceGenerator.createService(Service.class);
        Call<List<FixturesModel>> call = service.getmAllTeamFixtures(currentteamName,Service.sortDate,Service.order);

        call.enqueue(new Callback<List<FixturesModel>>() {
            @Override
            public void onResponse(Call<List<FixturesModel>> call, Response<List<FixturesModel>> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        List<FixturesModel> fixtureModelList = response.body();
                        for (int i = 0; i < fixtureModelList.size(); i++){
                            String week = fixtureModelList.get(i).getFixture_week();
                            String league = fixtureModelList.get(i).getFixture_league();
                            String score = fixtureModelList.get(i).getScore();
                            String date = fixtureModelList.get(i).getDate();
                            String awayLogo = fixtureModelList.get(i).getAwayLogo();
                            String homeLogo = fixtureModelList.get(i).getHomeLogo();
                            String away = fixtureModelList.get(i).getAway();
                            String home = fixtureModelList.get(i).getHome();


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


    private void GetGlobalFixtureItem() {
        ProgressDialogShow("Fixtures Loading...");
        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        Service service = DataServiceGenerator.createService(Service.class);
        Call<List<FixturesModel>> call = service.getmAllFixtures(currentleauge, String.valueOf(currentweek),Service.sortDate,Service.order);

        call.enqueue(new Callback<List<FixturesModel>>() {
            @Override
            public void onResponse(Call<List<FixturesModel>> call, Response<List<FixturesModel>> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        List<FixturesModel> fixtureModelList = response.body();
                        for (int i = 0; i < fixtureModelList.size(); i++){
                            String week = fixtureModelList.get(i).getFixture_week();
                            String league = fixtureModelList.get(i).getFixture_league();
                            String score = fixtureModelList.get(i).getScore();
                            String date = fixtureModelList.get(i).getDate();
                            String awayLogo = fixtureModelList.get(i).getAwayLogo();
                            String homeLogo = fixtureModelList.get(i).getHomeLogo();
                            String away = fixtureModelList.get(i).getAway();
                            String home = fixtureModelList.get(i).getHome();

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
        fixturesAdapter = new FixturesAdapter(FixturesActivity.this, fixtureDbList);
        viewPager.setAdapter(fixturesAdapter);
        viewPager.setPadding(100,0,100,0);
        fixturesAdapter.notifyDataSetChanged();


        Log.d("TestER",fixturesAdapter.getCount() + "");
        Log.d("TestER",fixtureDbList.size() + "");

        ProgressDialogHide();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

                Log.d("TestER",i + "");
            }
            @Override
            public void onPageSelected(int i) {

            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void ProgressDialogShow(String message) {
        progressDialog = new ProgressDialog(FixturesActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    private void ProgressDialogHide() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}