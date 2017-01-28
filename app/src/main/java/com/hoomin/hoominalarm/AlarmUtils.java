package com.hoomin.hoominalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.hoomin.hoominalarm.receiver.AlarmReceiver;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Hoo on 2017-01-28.
 */

public class AlarmUtils {
    //    public static void addAlarm(Context context, int id, int hour, int minute,int dayOfWeek ) {
//
//        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("id",id);
//        bundle.putInt("hour", hour);
//        bundle.putInt("minute", minute);
//        bundle.putInt("dayOfWeek",dayOfWeek);
//        alarmIntent.putExtra("time",bundle);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarmIntent, 0);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minute);
//
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
////            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, pendingIntent);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        } else {
//            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        }
//    }
    public static void deleteAlarm(Context context, int id) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                id, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        manager.cancel(pendingIntent);

        Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }

    public static void addAlarm(Context context, Repo repo) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("alarm", repo);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, repo.get_id(), alarmIntent, 0);

        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, repo.getHour());
        calendar.set(Calendar.MINUTE, repo.getMinutes());

        if (calendar.getTimeInMillis() <= now.getTimeInMillis()) {
            calendar.setTimeInMillis(calendar.getTimeInMillis()+24*60*60*1000);
            long remainTime=calendar.getTimeInMillis()-now.getTimeInMillis();
            long hour = remainTime/(60*60*1000);
            Toast.makeText(context,String.valueOf(hour)+"시간 남음",Toast.LENGTH_SHORT).show();
        } else {
        }

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

    }
}
