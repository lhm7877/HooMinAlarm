package com.hoomin.hoominalarm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Hoo on 2017-01-27.
 */

public class Repo extends RealmObject implements Serializable {

    @PrimaryKey
    private int _id;
    private int hour;
    private int minutes;
    private int dayOfWeek;
    private boolean enabled;
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

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int daysOfWeek) {
        this.dayOfWeek = daysOfWeek;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean ebabled) {
        this.enabled = ebabled;
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
