package com.hoomin.hoominalarm;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_main)
    protected RecyclerView rv_main;

    private RecyclerView.Adapter rv_adapter;
    private RecyclerView.LayoutManager rv_layoutManager;

    private Realm mRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //TODO: setHasFixedSize 이유?
        rv_main.setHasFixedSize(true);

        mRealm = Realm.getDefaultInstance();

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RealmResults<Repo> mResults = mRealm.where(Repo.class).findAll();
//        Log.i("resultsize", String.valueOf(mResults.size()));
        mResults.addChangeListener(new RealmChangeListener<RealmResults<Repo>>() {
            @Override
            public void onChange(RealmResults<Repo> element) {
                    rv_adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_plus:
                Intent intent = new Intent(this,ManageAlarmActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initData() {

        RealmResults<Repo> mResults = mRealm.where(Repo.class).findAll();
        rv_layoutManager = new LinearLayoutManager(this);
        rv_main.setLayoutManager(rv_layoutManager);
        rv_adapter = new RVAdapter(mRealm, mResults);

        //TODO : setHasStableIds 뜻?
        rv_adapter.setHasStableIds(true);
        rv_main.setAdapter(rv_adapter);
        rv_main.addItemDecoration(new DividerItemDecoration(rv_main.getContext(),
                DividerItemDecoration.VERTICAL));
    }
}
