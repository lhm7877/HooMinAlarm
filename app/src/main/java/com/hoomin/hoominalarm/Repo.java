package com.hoomin.hoominalarm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hoo on 2017-01-27.
 */

public class Repo extends RealmObject {

    @PrimaryKey
    private int _id;
    private int hour;
    private int minutes;
    private int daysOfWeek;
    private boolean ebabled;
    private int vibrate;
    private String message;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(int daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public boolean isEbabled() {
        return ebabled;
    }

    public void setEbabled(boolean ebabled) {
        this.ebabled = ebabled;
    }

    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
