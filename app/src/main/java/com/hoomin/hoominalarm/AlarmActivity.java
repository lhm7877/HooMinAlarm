package com.hoomin.hoominalarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AlarmActivity extends AppCompatActivity {
    @BindView(R.id.ibtn_cancle)
    ImageButton imageButton;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    private MediaPlayer mp;
    Bundle bundle;
    Vibrator vibrator;
    Repo repo;
    int vibrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        int id = getIntent().getIntExtra("alarm",0);
        Log.i("id", String.valueOf(id));
        Realm mRealm = Realm.getDefaultInstance();
        Repo repo = mRealm.where(Repo.class).equalTo("_id", id).findFirst();
        Log.i("repo", String.valueOf(repo.getMinutes()));
        Log.i("myBoot", String.valueOf(repo.getVibrate())+"진동");
        vibrate = repo.getVibrate();
        vibrator =(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        startAlarm(vibrate);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlarm(vibrate);
                finish();
            }
        });
        tv_time.setText(repo.getHour() +" : "+repo.getMinutes());

    }
    private void startAlarm(int vibrate){
        if(vibrate==1){
            vibrator.vibrate(2000);
        }else {
            //볼륨 조절 가능하게
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            //TODO: 스위치로 진동 구분
            //TODO : 미디어볼륨 -> 알람 볼륨
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mp = MediaPlayer.create(getApplicationContext(), alert);
            mp.setLooping(true);
            mp.start();
        }
    }
    private void stopAlarm(int vibrate){
        if(vibrate==0){
            if(mp!=null) {
                mp.stop();
                mp.release();
                mp = null;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAlarm(vibrate);
    }
}
