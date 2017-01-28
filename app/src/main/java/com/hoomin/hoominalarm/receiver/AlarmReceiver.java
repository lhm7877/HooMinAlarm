package com.hoomin.hoominalarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.hoomin.hoominalarm.AlarmActivity;
import com.hoomin.hoominalarm.AlarmUtils;
import com.hoomin.hoominalarm.Repo;

import java.util.Calendar;

import io.realm.Realm;

import static com.hoomin.hoominalarm.UIUtils.DAYOFWEEK;

/**
 * Created by Hoo on 2017-01-28.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Bundle bundle;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startIntent = new Intent(context, AlarmActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Realm mRealm = Realm.getDefaultInstance();
        Repo repo = mRealm.where(Repo.class).equalTo("_id", intent.getIntExtra("alarm",0)).findFirst();

        startIntent.putExtra("alarm", repo.get_id());

        Log.i("myBoot", String.valueOf(repo.getDayOfWeek()));
        int dayOfWeek = repo.getDayOfWeek();

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, repo.getHour());
        cal.set(Calendar.MINUTE, repo.getMinutes());
        Log.i("mylog", String.valueOf(dayOfWeek)+"값");
        if (dayOfWeek != 0) {
            cal = Calendar.getInstance();
            //1이 일요일 2가 화요일
            if ((dayOfWeek & DAYOFWEEK[cal.get(Calendar.DAY_OF_WEEK) - 1]) != 0) {
                //알람이 울린다.
                context.startActivity(startIntent);
                AlarmUtils.addAlarm(context, repo.get_id());
            }

        } else {
            Log.i("mylog","dayOfWeek가 0일때");
            AlarmUtils.addAlarm(context, repo.get_id());
            context.startActivity(startIntent);
        }


    }
}
