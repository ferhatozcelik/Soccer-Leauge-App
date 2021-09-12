package com.ferhatozcelik.soccerleauge.network;


import com.ferhatozcelik.soccerleauge.FixturesModel;
import com.ferhatozcelik.soccerleauge.LeaugeModel;
import com.ferhatozcelik.soccerleauge.PointModel;
import com.ferhatozcelik.soccerleauge.database.Point;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @GET("leagues")
    Call<List<LeaugeModel>> getLeauge();

    //_sort=point&_order=desc

    String sortPoint = "point,average";
    String orderdesc = "asc";

    @GET("leagues-points?")
    Call<List<PointModel>> getPointItem(@Query("points_league") String points_league, @Query("points_week") String points_week, @Query("_sort") String sort, @Query("_order") String order);

    @GET("fixtures?")
    Call<List<FixturesModel>> getmAllFixtures(@Query("fixture_league") String fixture_league, @Query("fixture_week") String fixture_week);
}
