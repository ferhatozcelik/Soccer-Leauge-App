package com.ferhatozcelik.soccerleauge.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ferhatozcelik.soccerleauge.R;
import com.ferhatozcelik.soccerleauge.database.Fixtures;

import java.util.ArrayList;
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
        TextView score = view.findViewById(R.id.score_item);

        Fixtures fixturesModel = mPosts.get(position);
        score.setText(fixturesModel.getScore());

        container.addView(view,position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}