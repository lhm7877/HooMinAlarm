package com.hoomin.hoominalarm;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hooo on 2017-01-25.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ListItemViewHolder> {
    private ArrayList<RVData> rvDatas;

    public RVAdapter(ArrayList<RVData> rvDatas) {
        this.rvDatas = rvDatas;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: 왜 fasle?
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main,parent,false);
        ListItemViewHolder viewHolder = new ListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        holder.tv_time.setText(rvDatas.get(position).textTime);
    }

    @Override
    public int getItemCount() {
        return rvDatas.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_time;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
        }
    }
}

class RVData{
    public String textTime;

    public RVData(String textTime) {
        this.textTime = textTime;
    }
}
