package com.hoomin.hoominalarm;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

/**
 * Created by Hoo on 2017-01-28.
 */

public class UIUtils {
    Context mContext;
    static Resources res;

    public static final int[] DAYOFWEEK = {1, 2, 4, 8, 16, 32, 64};
    private static String[] dayOfWeeks = {"일요일","월요일","화요일","수요일","목요일","금요일","토요일"};
    public static String getStringDayOfWeek(int dayOfWeekNu){
        String dayOfWeek = "";
        for(int i = 0; i<7; i++){
            if ((dayOfWeekNu & DAYOFWEEK[i]) != 0){
                dayOfWeek += dayOfWeeks[i]+" ";
            }
        }
        return dayOfWeek;
    }
}
