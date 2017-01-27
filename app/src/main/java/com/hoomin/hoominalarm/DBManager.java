package com.hoomin.hoominalarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//TODO : sqlite 안씀
/**
 * Created by Hooo on 2017-01-25.
 */

public class DBManager extends SQLiteOpenHelper{
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE alarms( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hour INTEGER, " +
                "minutes INTEGER, " +
                "daysofweek INTEGER, " +
                "alarmtime LONG, " +
                "enabled INTEGER, " +
                "vibrate INTEGER, " +
                "message TEXT, " +
                "alert TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
