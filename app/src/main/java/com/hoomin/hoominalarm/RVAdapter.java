package com.hoomin.hoominalarm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Hooo on 2017-01-25.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ListItemViewHolder> {
    private ArrayList<RVData> rvDatas;
    private Realm mRealm;
    private RealmResults<Repo> mResults;

    public RVAdapter(Realm realm, RealmResults<Repo> results) {
        this.mRealm = realm;
        this.mResults = results;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: 왜 fasle?
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main,parent,false);
        ListItemViewHolder viewHolder = new ListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListItemViewHolder holder, final int position) {
        holder.item_tv_time.setText(String.valueOf(mResults.get(position).getHour()+" : "+mResults.get(position).getMinutes()));
        holder.item_linearLayouot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ManageAlarmActivity.class);

                //TODO : 진동 여부 등
                Bundle bundle = new Bundle();
                bundle.putInt("id",mResults.get(position).get_id());
                bundle.putInt("hour",mResults.get(position).getHour());
                bundle.putInt("minute",mResults.get(position).getMinutes());
                bundle.putInt("dayOfWeek",mResults.get(position).getDayOfWeek());
                bundle.putBoolean("ebabled",mResults.get(position).isEnabled());
                bundle.putInt("vibrate",mResults.get(position).getVibrate());
                bundle.putString("message",mResults.get(position).getMessage());

                intent.putExtra("Bundle",bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView item_tv_time;
        public LinearLayout item_linearLayouot;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            item_tv_time = (TextView)itemView.findViewById(R.id.item_tv_time);
            item_linearLayouot = (LinearLayout)itemView.findViewById(R.id.item_linearLayouot);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

class RVData{
    public String textTime;

    public RVData(String textTime) {
        this.textTime = textTime;
    }
}
