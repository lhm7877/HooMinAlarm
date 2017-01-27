package com.hoomin.hoominalarm;

import android.app.AlarmManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_main)
    protected RecyclerView rv_main;

    private RecyclerView.Adapter rv_adapter;
    private RecyclerView.LayoutManager rv_layoutManager;
    private ArrayList<RVData> rvDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: setHasFixedSize 이유?
        rv_main.setHasFixedSize(true);

        //Linearlayout을 사용한다.
        rv_layoutManager = new LinearLayoutManager(this);
        rv_main.setLayoutManager(rv_layoutManager);

        rvDatas = new ArrayList<>();
        rv_adapter = new RVAdapter(rvDatas);
        rv_main.setAdapter(rv_adapter);

        rvDatas.add(new RVData("6:30"));
        rvDatas.add(new RVData("8:30"));
        rvDatas.add(new RVData("10:00"));
    }
}
