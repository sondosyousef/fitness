package com.example.fitness;

import androidx.annotation.BinderThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    EditText add_Time;
    TextView TEXT;
    private int time =0;
    int upTime;
    private boolean running;
    Button goBack , BTNAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        goBack = findViewById(R.id.goBACK);
        add_Time=findViewById(R.id.addTime);
        TEXT = findViewById(R.id.textTimer);
        BTNAdd = findViewById(R.id.button);

        BTNAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= add_Time.getText().toString();
                upTime = Integer.parseInt(s);
                time=upTime;
                upDateText();


            }
        });


        if(savedInstanceState !=null){
            time = savedInstanceState.getInt("time");
            running = savedInstanceState.getBoolean("running");
        }


        runTimer();
    }

    private void upDateText() {
        String t = add_Time.getText().toString();
        int mtime= Integer.parseInt(t);
        int houre = (int)(mtime/1000)/3600;
        int min = (int)((mtime/1000)%3600)/60;
        int sec = (int)(mtime/1000)%60;
        String timeleft ;
        if(houre>0) {
            timeleft = String.format(Locale.getDefault(), "%d:%02d:%02d", houre,min, sec);
        }
        else
        {
            timeleft = String.format(Locale.getDefault(), "%02d:%02d", min, sec);

        }

        TEXT.setText(timeleft);


    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putInt("time", time);
        bundle.putBoolean("running", running);
    }


    public void onClickStart(View view) {

        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        time = 0;
        running = false;

    }


        private void runTimer(){

            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int hours = time/3600;
                    int minutes = time % 3600 /60;
                    int snds = time % 60;
                    String finaltime = String.format(Locale.getDefault(),
                            "%d:%02d:%02d", hours, minutes, snds);
                    TEXT.setText(finaltime);
                    if(running ){
                        if (time !=0) {
                            --time;
                        }
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    public void setGoBack(View view){
        Intent intent = new Intent(this ,MainActivity.class);
        startActivity(intent);
    }


    }

