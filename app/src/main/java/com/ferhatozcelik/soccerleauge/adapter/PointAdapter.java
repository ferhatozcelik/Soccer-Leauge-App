package com.ferhatozcelik.soccerleauge.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ferhatozcelik.soccerleauge.FixturesActivity;
import com.ferhatozcelik.soccerleauge.FixturesModel;
import com.ferhatozcelik.soccerleauge.FixturesViewModel;
import com.ferhatozcelik.soccerleauge.R;
import com.ferhatozcelik.soccerleauge.database.Fixtures;
import com.ferhatozcelik.soccerleauge.database.Point;
import com.ferhatozcelik.soccerleauge.network.DataServiceGenerator;
import com.ferhatozcelik.soccerleauge.network.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Point> mPosts;

    private FixturesViewModel fixtureViewModel;
    private ArrayList<Fixtures> fixtureDbList;
    private ViewPager viewPager;

    public PointAdapter(Context context, List<Point> posts){
        mContext = context;
        mPosts = posts;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.point_list_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Point post = mPosts.get(position);

        GetReadView(holder.teamname,post.getTeamname());
        GetReadView(holder.pointitem_o,"O:" + post.getPlay());
        GetReadView(holder.pointitem_g,"G:" + post.getWin());
        GetReadView(holder.pointitem_b,"B:" + post.getTie());
        GetReadView(holder.pointitem_m,"M:" + post.getLose());
        GetReadView(holder.pointitem_av,"AV:" + post.getAverage());
        GetReadView(holder.pointitem_p,"P:" + post.getPoint());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, FixturesActivity.class);
                i.putExtra("fixturestype","teamfixture");
                i.putExtra("currentleaugeName",post.getLeague());
                i.putExtra("currentweek",Integer.valueOf(post.getWeek()));
                i.putExtra("teamName",post.getTeamname());
                mContext.startActivity(i);

            }
        });
    }


    private void GetReadView(TextView textView,String text) {
            textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView teamname,pointitem_o,pointitem_g,pointitem_b,pointitem_m,pointitem_av,pointitem_p;

        public ImageViewHolder(View itemView) {
            super(itemView);
            teamname = itemView.findViewById(R.id.teamname);
            pointitem_o = itemView.findViewById(R.id.pointitem_o);
            pointitem_g = itemView.findViewById(R.id.pointitem_g);
            pointitem_b = itemView.findViewById(R.id.pointitem_b);
            pointitem_m = itemView.findViewById(R.id.pointitem_m);
            pointitem_av = itemView.findViewById(R.id.pointitem_av);
            pointitem_p = itemView.findViewById(R.id.pointitem_p);

        }
    }
}