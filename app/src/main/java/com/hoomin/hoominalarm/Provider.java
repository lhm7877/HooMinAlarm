package com.hoomin.hoominalarm;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Hooo on 2017-01-26.
 */

public class Provider extends ContentProvider {
    static final Uri CONTENT_URI = Uri.parse("content://com.hoomin.Alarm2/Repo");
    static final int ALLWORD = 1;
    static final int ONEWORD = 2;
    static final UriMatcher Matcher;
    private Realm realm;
    static final String[] sColumns = new String[]{"id","name","full_name"};


    static {
        Matcher = new UriMatcher(UriMatcher.NO_MATCH);
        Matcher.addURI("com.hoomin.Alarm2", "Repo", ALLWORD);
        Matcher.addURI("com.hoomin.Alarm2", "Repo/*", ONEWORD);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        realm.beginTransaction();
        try {
            for(ContentValues value : values){
                Repo responseBody = realm.createObject(Repo.class);
                responseBody.set_id(value.getAsInteger(sColumns[0]));
                responseBody.setHour(value.getAsInteger(sColumns[1]));
                responseBody.setMinutes(value.getAsInteger(sColumns[2]));
                responseBody.setDayOfWeek(value.getAsInteger(sColumns[3]));
                responseBody.setEnabled(value.getAsBoolean(sColumns[4]));
                responseBody.setVibrate(value.getAsInteger(sColumns[5]));
                responseBody.setMessage(value.getAsString(sColumns[6]));
            }
        }finally {
            realm.commitTransaction();
        }
        return values.length;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        realm = Realm.getDefaultInstance();
        RealmQuery<Repo> query = realm.where(Repo.class);
        RealmResults<Repo> results = query.findAll();
        MatrixCursor matrixCursor = new MatrixCursor(sColumns);
        for(Repo item : results){
            Object[] rowData= new Object[]{item.get_id(),item.getHour(),item.getMinutes(),
                    item.getDayOfWeek(),item.isEnabled(),item.getVibrate(),item.getMessage()};
            matrixCursor.addRow(rowData);
        }

        return matrixCursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        realm.beginTransaction();
        Repo responseBody = realm.createObject(Repo.class);
        responseBody.set_id(values.getAsInteger(sColumns[0]));
        responseBody.setHour(values.getAsInteger(sColumns[1]));
        responseBody.setMinutes(values.getAsInteger(sColumns[2]));
        responseBody.setDayOfWeek(values.getAsInteger(sColumns[3]));
        responseBody.setEnabled(values.getAsBoolean(sColumns[4]));
        responseBody.setVibrate(values.getAsInteger(sColumns[5]));
        responseBody.setMessage(values.getAsString(sColumns[6]));
        realm.commitTransaction();
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.withAppendedPath(uri, String.valueOf(responseBody.get_id()));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
