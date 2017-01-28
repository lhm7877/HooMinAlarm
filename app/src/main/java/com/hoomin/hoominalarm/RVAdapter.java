package com.hoomin.hoominalarm;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Hooo on 2017-01-25.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ListItemViewHolder> {
    private Realm mRealm;
    private RealmResults<Repo> mResults;
    private Context context;
    private final RealmChangeListener listener;

    public RVAdapter(Realm realm, RealmResults<Repo> results) {
        this.mRealm = realm;
        this.mResults = results;
        this.listener = new RealmChangeListener<RealmResults<Repo>>() {
            @Override
            public void onChange(RealmResults<Repo> results) {
                notifyDataSetChanged();
            }
        };

    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: 왜 fasle?
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main, parent, false);
        ListItemViewHolder viewHolder = new ListItemViewHolder(view);
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListItemViewHolder holder, final int position) {
        holder.tv_dayOfWeek.setText(UIUtils.getStringDayOfWeek(mResults.get(position).getDayOfWeek()));
        holder.item_tv_time.setText(String.valueOf(mResults.get(position).getHour() + " : " + mResults.get(position).getMinutes()));
        holder.ibtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmUtils.deleteAlarm(context,mResults.get(position).get_id());
                onItemRemove(position);

            }
        });
        holder.item_linearLayouot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ManageAlarmActivity.class);

                //TODO : 진동 여부 등
                Bundle bundle = new Bundle();
                bundle.putInt("id", mResults.get(position).get_id());
                bundle.putInt("hour", mResults.get(position).getHour());
                bundle.putInt("minute", mResults.get(position).getMinutes());
                bundle.putInt("dayOfWeek", mResults.get(position).getDayOfWeek());
                bundle.putBoolean("ebabled", mResults.get(position).isEnabled());
                bundle.putInt("vibrate", mResults.get(position).getVibrate());
                bundle.putString("message", mResults.get(position).getMessage());

                intent.putExtra("Bundle", bundle);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void onItemRemove(int position) {
        mRealm.beginTransaction();
        Repo repo = mResults.get(position);
        repo.deleteFromRealm();
        mRealm.commitTransaction();

        notifyItemRemoved(position);
//        notifyItemRangeRemoved(position,mResults.size());
//        mRealm.close();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_tv_time)
        TextView item_tv_time;
        @BindView(R.id.item_linearLayouot)
        LinearLayout item_linearLayouot;
        @BindView(R.id.tv_dayOfWeek)
        TextView tv_dayOfWeek;
        @BindView(R.id.ibtn_delete)
        ImageButton ibtn_delete;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
