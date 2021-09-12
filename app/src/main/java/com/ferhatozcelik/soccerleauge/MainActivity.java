package com.ferhatozcelik.soccerleauge;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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

    private ArrayList<Integer> weekDbList;
    private ArrayAdapter<Integer> dataAdapterForWeek;
    private int currentweek = 0;
    private String currentleauge = null;
    private PointAdapter pointAdapter;
    private Button fixtureButton;
    private Context context = MainActivity.this;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        fetchLeauge();
        CreateWeekItem();
        ViewModelInit();

        fixtureButton = findViewById(R.id.fixtureButton);
        fixtureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,FixturesActivity.class);
                i.putExtra("currentleauge",currentleauge);
                i.putExtra("currentweek",currentweek);
                startActivity(i);
            }
        });



    }

    private void CreateWeekItem() {

        weekText = findViewById(R.id.weekText);
        nextWeek = findViewById(R.id.nextWeek);
        backWeek = findViewById(R.id.backWeek);

        currentweek = 0;


            nextWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentweek < 38){
                        currentweek += 1;
                        WeekUpdate();
                    }
                }
            });
            backWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentweek > 0){
                        currentweek -= 1;
                        WeekUpdate();
                    }
                }
            });


    }

    private void WeekUpdate() {
        weekText.setText(currentweek + ".Week");
        GetItem(currentleauge,currentweek);

    }

    private void ViewModelInit() {
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
        dataAdapterForLeauge = new ArrayAdapter<Leauge>(this, android.R.layout.simple_spinner_item, leaugeDbList);
        dataAdapterForLeauge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeauge.setAdapter(dataAdapterForLeauge);
        spinnerLeauge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Leauge leaugeModel = (Leauge) parent.getSelectedItem();
                currentleauge = leaugeModel.getKey();
                GetItem(currentleauge,currentweek);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void GetItem(String key, int i) {

        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        Service service = DataServiceGenerator.createService(Service.class);
        Call<List<PointModel>> call = service.getPointItem(key, String.valueOf(i),Service.sortPoint,Service.orderdesc);
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
        point_recyclerView = (RecyclerView)findViewById(R.id.point_recyclerView);
        point_recyclerView.setHasFixedSize(true);
        point_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        point_recyclerView = findViewById(R.id.point_recyclerView);
        point_recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);



        point_recyclerView.setLayoutManager(mLayoutManager);
        pointAdapter = new PointAdapter(this, pointDbList);
        point_recyclerView.setAdapter(pointAdapter);
        point_recyclerView.clearFocus();
        pointAdapter.notifyDataSetChanged();


    }

}