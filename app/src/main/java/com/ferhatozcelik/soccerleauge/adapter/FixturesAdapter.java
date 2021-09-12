package com.ferhatozcelik.soccerleauge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ferhatozcelik.soccerleauge.R;
import com.ferhatozcelik.soccerleauge.database.Fixtures;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FixturesAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Fixtures> mPosts;

    public FixturesAdapter(Context context, ArrayList<Fixtures> posts){
        this.mContext = context;
        this.mPosts = posts;
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.card_item,container,false);
        Fixtures fixturesModel = mPosts.get(position);


        TextView score = view.findViewById(R.id.scoreText);
        TextView teamhomeName = view.findViewById(R.id.teamhomeName);
        TextView teamawayName = view.findViewById(R.id.teamawayName);

        ImageView teamlogohome = view.findViewById(R.id.teamlogohome);
        ImageView teamlogoaway = view.findViewById(R.id.teamlogoaway);
        TextView dateText = view.findViewById(R.id.dateText);


        score.setText(fixturesModel.getScore());
        teamhomeName.setText(fixturesModel.getHome());
        teamawayName.setText(fixturesModel.getAway());


         SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Date date = null;
        try {
            date = dt.parse(fixturesModel.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        dateText.setText(dt1.format(date));


        Glide.with(mContext).load(fixturesModel.getAwayLogo())
                .apply(new RequestOptions().placeholder(R.drawable.card_background))
                .into(teamlogoaway);

        Glide.with(mContext).load(fixturesModel.getHomeLogo())
                .apply(new RequestOptions().placeholder(R.drawable.card_background))
                .into(teamlogohome);

        container.addView(view,position);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}