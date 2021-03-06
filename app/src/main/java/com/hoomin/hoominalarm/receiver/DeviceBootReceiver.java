package com.hoomin.hoominalarm.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.hoomin.hoominalarm.AlarmUtils;
import com.hoomin.hoominalarm.MainActivity;
import com.hoomin.hoominalarm.Repo;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Hoo on 2017-01-28.
 */

public class DeviceBootReceiver extends BroadcastReceiver {
    private Realm mRealm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            mRealm = Realm.getDefaultInstance();
            RealmResults<Repo> mResults = mRealm.where(Repo.class).findAll();
            for(int i = 0;i<mResults.size();i++){
                Repo repo = mResults.get(i);
                AlarmUtils.addAlarm(context,mResults.get(i).get_id());
            }

        }
    }
}
