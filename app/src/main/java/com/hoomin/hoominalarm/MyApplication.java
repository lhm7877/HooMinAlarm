package com.hoomin.hoominalarm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Hooo on 2017-01-26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
