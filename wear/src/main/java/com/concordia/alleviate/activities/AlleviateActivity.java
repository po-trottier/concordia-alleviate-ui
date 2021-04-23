package com.concordia.alleviate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import com.concordia.alleviate.R;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class AlleviateActivity extends WearableActivity {

    private final String exercise_name = "Breathing Exercise 1";
    private final String[] commands = {"Breathe In", "Breathe Out"};
    private final int breathing_time = 7000;
    private final int progress_bar_precision = 1000;

    private TextView exercise_text;
    private TextView command_text;
    private CustomGauge timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alleviate);
        findViews();
        updateViews();
    }

    private void findViews() {
        exercise_text = findViewById(R.id.exercise_name);
        command_text = findViewById(R.id.command);
        timer = findViewById(R.id.alleviate_progress_bar);
    }

    private void updateViews() {
        exercise_text.setText(exercise_name);
        updateProgressBar();
    }

    private void updateProgressBar() {
        new Thread(() -> {
            try {
                for(int round=0 ; round<7 ; round++){
                    for (int i = 0 ; i < progress_bar_precision ; i++) {
                        float percentage = (float) (i) / progress_bar_precision;
                        final int r = round;
                        runOnUiThread(() -> updateValues(r, percentage));
                        Thread.sleep((long) ((float) (breathing_time/2/progress_bar_precision)));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateValues(int round, float percentage){
        if(round == 6){
            command_text.setText("Done");
        }
        else if(round % 2 == 0){
            command_text.setText(commands[0]);
            timer.setValue((int) (1000 * percentage));
        }
        else{
            command_text.setText(commands[1]);
            timer.setValue((int) (1000*(1-percentage)));
            timer.setSweepAngle((int) (-360*(percentage))-90);
        }
    }
}
