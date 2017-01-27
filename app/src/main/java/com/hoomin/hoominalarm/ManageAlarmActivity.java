package com.hoomin.hoominalarm;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EnumSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class ManageAlarmActivity extends AppCompatActivity {
    @BindView(R.id.ll_repeat)
    protected LinearLayout ll_repeat;
    @BindView(R.id.tv_repeat)
    protected TextView tv_repeat;
    @BindView(R.id.btn_accept)
    protected Button btn_accept;
    @BindView(R.id.btn_cancle)
    protected Button btn_cancle;
    @BindView(R.id.timepicker)
    protected TimePicker timePicker;

    //일주일을 이진수로 표현
//    private int[] dayOfWeek = {1, 2, 4, 8, 16, 32, 64};
    final int[] currentCheck = new int[]{0};
    private int mHour, mMinute;
    private Realm mRealm;

    Bundle bundle;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_alarm);

        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();
        timePicker.setIs24HourView(true);

        init();

        bundle = getIntent().getBundleExtra("Bundle");
        clickRepeatLayout();

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDB(id);
                finish();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getTime();

    }

    private void init() {
        if (bundle!=null) {
            modifyAlarm();

            currentCheck[0] = bundle.getInt("dayOfWeek");
        }
    }


    private void modifyAlarm() {

        //시간 초기값
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(bundle.getInt("hour"));
            timePicker.setMinute(bundle.getInt("minute"));
        } else {
            timePicker.setCurrentHour(bundle.getInt("hour"));
            timePicker.setCurrentMinute(bundle.getInt("minute"));
        }
        //반복 날짜 초기값
        tv_repeat.setText(String.valueOf(bundle.getInt("dayOfWeek")));

    }

    private void getTime() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHour = timePicker.getHour();
            mMinute = timePicker.getMinute();
        } else {
            mHour = timePicker.getCurrentHour();
            mMinute = timePicker.getCurrentMinute();
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                mHour = hour;
                mMinute = minute;
            }
        });
    }

    private void updateDB(int id) {
        mRealm.beginTransaction();

        //자동 증가
        if (!mRealm.isEmpty()) {
            id = (int) (mRealm.where(Repo.class).max("_id").intValue() + 1);
        }
        if (bundle != null) {
            id = bundle.getInt("id");
        }

//        Repo repo = mRealm.createObject(Repo.class, id);
        Repo repo = new Repo();
        repo.set_id(id);
        repo.setHour(mHour);
        repo.setMinutes(mMinute);
        repo.setDayOfWeek(currentCheck[0]);

        mRealm.copyToRealmOrUpdate(repo);
        mRealm.commitTransaction();

    }

    //언제 반복할지 정하는 레이아웃 클릿
    private void clickRepeatLayout() {
        final Resources res = getResources();


        ll_repeat = (LinearLayout) findViewById(R.id.ll_repeat);
        ll_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //체크박스에 보여줄 리스트
                final String[] dayOfWeeks = res.getStringArray(R.array.dayofweek);
                //TODO: 왜 final+배열 사용?

//                if (!tv_repeat.getText().equals("안 함")) {
//                    currentCheck[0] = Integer.valueOf(tv_repeat.getText().toString());
//                }

                boolean[] dayOfWeekCheck = new boolean[7];
                for (int i = 0; i < dayOfWeekCheck.length; i++) {
                    if ((currentCheck[0] & UIUtils.DAYOFWEEK[i]) != 0) dayOfWeekCheck[i] = true;
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(ManageAlarmActivity.this);
                dialog.setTitle(R.string.repeat)
                        .setMultiChoiceItems(dayOfWeeks,
                                dayOfWeekCheck
                                , new DialogInterface.OnMultiChoiceClickListener() {
                                    int tempCurrentCheck = currentCheck[0];

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which, boolean isChecked) {
                                        if (isChecked) {
                                            tempCurrentCheck += Math.pow(2, which);
                                            currentCheck[0] = tempCurrentCheck;
                                        } else {
                                            tempCurrentCheck -= Math.pow(2, which);
                                            currentCheck[0] = tempCurrentCheck;
                                        }
                                    }
                                })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_repeat.setText(String.valueOf(UIUtils.getStringDayOfWeek(currentCheck[0])));
                                Log.i("string",String.valueOf(UIUtils.getStringDayOfWeek(currentCheck[0])));
                            }
                        })
                        .setNegativeButton(R.string.no, null).create().show();
            }
        });
    }
}
