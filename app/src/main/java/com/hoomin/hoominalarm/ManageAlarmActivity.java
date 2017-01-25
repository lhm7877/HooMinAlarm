package com.hoomin.hoominalarm;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EnumSet;

public class ManageAlarmActivity extends AppCompatActivity {
    private LinearLayout ll_repeat;
    private TextView tv_repeat;
    private int[] dayOfWeek = {1,2,4,8,16,32,64};
    private Button btn_accept, btn_cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_alarm);

        tv_repeat = (TextView)findViewById(R.id.tv_repeat);
        btn_accept = (Button)findViewById(R.id.btn_accept);
        btn_cancle = (Button)findViewById(R.id.btn_cancle);

        clickRepeatLayout();
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void clickRepeatLayout(){
        final Resources res = getResources();


        ll_repeat = (LinearLayout)findViewById(R.id.ll_repeat);
        ll_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //체크박스에 보여줄 리스트
                final String[] dayOfWeeks = res.getStringArray(R.array.dayofweek);
                //TODO: 왜 final+배열 사용?
                final int[] currentCheck = new int[1];
                if(tv_repeat.getText().equals("안 함")) {
                    currentCheck[0] = 0;
                }else{
                    currentCheck[0] = Integer.valueOf(tv_repeat.getText().toString());
                }

                boolean[] dayOfWeekCheck = new boolean[7];
                for(int i=0; i<dayOfWeekCheck.length; i++){
                    if( (currentCheck[0] &dayOfWeek[i]) !=0) dayOfWeekCheck[i]=true;
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(ManageAlarmActivity.this);
                dialog  .setTitle(R.string.repeat)
                        .setMultiChoiceItems(dayOfWeeks,
                                dayOfWeekCheck
                                , new DialogInterface.OnMultiChoiceClickListener() {
                                    int tempCurrentCheck = currentCheck[0];
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which, boolean isChecked) {
                                        if(isChecked){
                                            tempCurrentCheck+=Math.pow(2,which);
                                            currentCheck[0] =tempCurrentCheck;
//                                            tv_repeat.setText(String.valueOf(tempCurrentCheck));
                                        } else {
                                            tempCurrentCheck-=Math.pow(2,which);
                                            currentCheck[0] =tempCurrentCheck;
//                                            tv_repeat.setText(String.valueOf(tempCurrentCheck));
                                        }
                                    }
                                })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_repeat.setText(String.valueOf(currentCheck[0]));
                            }
                        })
                        .setNegativeButton(R.string.no,null).create().show();
            }
        });
    }
}
