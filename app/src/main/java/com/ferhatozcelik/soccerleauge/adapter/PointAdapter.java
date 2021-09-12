package com.ferhatozcelik.soccerleauge.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ferhatozcelik.soccerleauge.R;
import com.ferhatozcelik.soccerleauge.database.Point;

import java.util.List;


public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Point> mPosts;

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