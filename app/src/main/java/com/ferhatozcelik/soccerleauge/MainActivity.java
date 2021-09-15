package com.ferhatozcelik.soccerleauge;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.ferhatozcelik.soccerleauge.adapter.PointAdapter;
import com.ferhatozcelik.soccerleauge.database.Leauge;
import com.ferhatozcelik.soccerleauge.database.Point;
import com.ferhatozcelik.soccerleauge.database.PointDao;
import com.ferhatozcelik.soccerleauge.database.PointRoomDatabase;
import com.ferhatozcelik.soccerleauge.network.DataServiceGenerator;
import com.ferhatozcelik.soccerleauge.network.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.internal.http2.PushObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Ferhat OZCELIK
 */
public class MainActivity extends AppCompatActivity {
    private List<Leauge> leaugeList;
    private List<Point> pointList;

    private LeaugeViewModel leaugeViewModel;
    private PointViewModel pointViewModel;

    private Spinner spinnerLeauge;
    private TextView weekText;
    private ImageButton backWeek;
    private ImageButton nextWeek;

    private ArrayList<Leauge> leaugeDbList;
    private ArrayList<Point> pointDbList;

    private ArrayAdapter<Leauge> dataAdapterForLeauge;
    private RecyclerView point_recyclerView;

    private int currentweek = 0;
    private String currentleauge = null,currentName = null;
    private PointAdapter pointAdapter;
    private Button fixtureButton;
    private Context context = MainActivity.this;

    private ProgressDialog progressDialog;

    private int animasyontype = 1;
    private Button darkMode;
    SharedPreferences sharedPref;
    private int theme = 0;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPref.getInt("theme",0);
        if (theme == 0){
            setTheme(R.style.LightTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
		setContentView(R.layout.activity_main);

        point_recyclerView = findViewById(R.id.point_recyclerView);
        fetchLeauge();
        CreateWeekItem();
        ViewModelInit();

        darkMode = findViewById(R.id.darkMode);
        if (theme == 0){
            darkMode.setText("Dark Mode");
        }else{
            darkMode.setText("Light Mode");
        }

        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();

                if (theme == 0){
                    theme = 1;
                    editor.putInt("theme",theme);
                    editor.apply();
                }else if(theme == 1) {
                    theme = 0;
                    editor.putInt("theme",theme);
                    editor.apply();
                }

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });

    }

    private void ProgressBorShow(String message) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    private void ProgressBorHide() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            AnimasyonStart();
        }
    }

    private void AnimasyonStart() {

        if (animasyontype == 1){
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
            point_recyclerView.startAnimation(animation);
        }else   if (animasyontype == 2){
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_int_right);
            point_recyclerView.startAnimation(animation);
        }

    }


    private void CreateWeekItem() {

        weekText = findViewById(R.id.weekText);
        nextWeek = findViewById(R.id.nextWeek);
        backWeek = findViewById(R.id.backWeek);

        currentweek = 1;


            nextWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentweek < 38){
                        animasyontype = 1;
                        currentweek += 1;
                        WeekUpdate();
                    }
                }
            });
            backWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentweek > 1){
                        animasyontype = 2;
                        currentweek -= 1;
                        WeekUpdate();
                    }
                }
            });


    }

    private void WeekUpdate() {
        weekText.setText(currentweek + ".Week");
        fixtureButton.setText(currentweek + ".WEEK GLOBAL DRAW FIXTURE");
        GetItem(currentleauge,currentweek);

    }

    private void ViewModelInit() {


        fixtureButton = findViewById(R.id.fixtureButton);
        fixtureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,FixturesActivity.class);
                i.putExtra("fixturestype","globalfixture");
                i.putExtra("currentleauge",currentleauge);
                i.putExtra("currentleaugeName",currentName);
                i.putExtra("currentweek",currentweek);
                startActivity(i);
            }
        });



        leaugeDbList = new ArrayList<>();
        leaugeViewModel = ViewModelProviders.of(this).get(LeaugeViewModel.class);
        leaugeViewModel.getAllLeauge().observe(this, new Observer<List<Leauge>>() {
            @Override
            public void onChanged(@Nullable final List<Leauge> leauges) {
                leaugeList = leauges;
            }
        });
        pointDbList = new ArrayList<>();
        pointViewModel = ViewModelProviders.of(this).get(PointViewModel.class);
        pointViewModel.getAllPoint().observe(this, new Observer<List<Point>>() {
            @Override
            public void onChanged(@Nullable final List<Point> point) {
                pointList = point;
            }
        });
    }

    private void fetchLeauge(){
        ProgressBorShow("Leagues Loading...");
        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        Service service = DataServiceGenerator.createService(Service.class);
        Call<List<LeaugeModel>> call = service.getLeauge();

        call.enqueue(new Callback<List<LeaugeModel>>() {
            @Override
            public void onResponse(Call<List<LeaugeModel>> call, Response <List<LeaugeModel>> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        List<LeaugeModel> leaugeModelList = response.body();
                        for (int i = 0; i < leaugeModelList.size(); i++){
                            String key = leaugeModelList.get(i).getKey();
                            String league = leaugeModelList.get(i).getLeague();
                            Leauge leauge = new Leauge(league,key);
                            leaugeViewModel.insert(leauge);
                            leaugeDbList.add(leauge);
                            Log.d("MainActivityleaugeList:",key + "|" + league);
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SpinnerInit();
                        }
                    }, 3000);

                }
                }
            }
            @Override
            public void onFailure(Call<List<LeaugeModel>> call, Throwable t) {
                Log.d("MainActivityOnFailure:",t.getMessage());
            }
        });
    }

    private void SpinnerInit() {
        spinnerLeauge = findViewById(R.id.spinner);
        dataAdapterForLeauge = new ArrayAdapter<Leauge>(this, R.layout.spinner_item,R.id.sp_item, leaugeDbList);
        dataAdapterForLeauge.setDropDownViewResource(R.layout.spinner_item);
        spinnerLeauge.setAdapter(dataAdapterForLeauge);
        ProgressBorHide();
        spinnerLeauge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Leauge leaugeModel = (Leauge) parent.getSelectedItem();
                currentleauge = leaugeModel.getKey();
                currentName = leaugeModel.getLeague();
                GetItem(currentleauge,currentweek);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void GetItem(String key, int i) {

        ProgressBorShow("Teams Loading...");
        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        Service service = DataServiceGenerator.createService(Service.class);
        Call<List<PointModel>> call = service.getPointItem(key, String.valueOf(i),Service.sortPoint,Service.order);
        pointDbList.clear();
        call.enqueue(new Callback<List<PointModel>>() {
            @Override
            public void onResponse(Call<List<PointModel>> call, Response <List<PointModel>> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        List<PointModel> pointModelList = response.body();
                        for (int i = 0; i < pointModelList.size(); i++){
                            String week = pointModelList.get(i).getWeek();
                            String league = pointModelList.get(i).getLeague();
                            String teamname = pointModelList.get(i).getTeamname();
                            String play = pointModelList.get(i).getPlay();
                            String win = pointModelList.get(i).getWin();
                            String lose = pointModelList.get(i).getLose();
                            String tie = pointModelList.get(i).getTie();
                            String averange = pointModelList.get(i).getAverage();
                            String point = pointModelList.get(i).getPoint();

                            Point pointModel = new Point(week,league,teamname,play,win,lose,tie,averange,point);
                            pointViewModel.insert(pointModel);
                            pointDbList.add(pointModel);
                            Log.d("MainActivityleaugeList:",key + "|" + league);

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
            public void onFailure(Call<List<PointModel>> call, Throwable t) {
                Log.d("MainActivityOnFailure:",t.getMessage());
            }
        });

    }

    private void takeActionView() {

        point_recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        point_recyclerView.setLayoutManager(mLayoutManager);
        pointAdapter = new PointAdapter(this, pointDbList);
        point_recyclerView.setAdapter(pointAdapter);
        ProgressBorHide();
        point_recyclerView.clearFocus();
        pointAdapter.notifyDataSetChanged();


    }

}
