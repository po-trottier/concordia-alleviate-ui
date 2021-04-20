package com.concordia.alleviate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.lifecycle.MutableLiveData;
import com.concordia.alleviate.R;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class MainActivity extends WearableActivity {

    private static final int GAUGE_ANIMATION_TIME = 5;

    private static final int STRESS_MINIMUM = 10;
    private static final int STRESS_MAXIMUM = 95;
    private static final int ALLEVIATED_MINIMUM = 1;
    private static final int ALLEVIATED_MAXIMUM = 14;
    private static final int HEART_RATE_MINIMUM = 60;
    private static final int HEART_RATE_MAXIMUM = 140;

    private int stressLevel;
    private int alleviatedTime;
    private int heartRate;

    private CustomGauge stressGauge;
    private TextView stressView;
    private TextView alleviatedView;
    private TextView heartRateView;
    private ImageButton vitalsButton;
    private ImageButton alleviateButton;
    private ImageButton reliefButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        generateRandomData();
        updateViews();
    }

    private void findViews() {
        stressGauge = findViewById(R.id.home_progress_bar);
        stressView = findViewById(R.id.home_stress_level_text);
        alleviatedView = findViewById(R.id.home_alleviated_text);
        heartRateView = findViewById(R.id.home_heart_rate_text);
        vitalsButton = findViewById(R.id.home_vitals_button);
        alleviateButton = findViewById(R.id.home_alleviate_button);
        reliefButton = findViewById(R.id.home_relief_button);
    }

    private void generateRandomData() {
        stressLevel = (int) (Math.random() * (STRESS_MAXIMUM - STRESS_MINIMUM + 1) + STRESS_MINIMUM);
        alleviatedTime = (int) (Math.random() * (ALLEVIATED_MAXIMUM - ALLEVIATED_MINIMUM + 1) + ALLEVIATED_MINIMUM);
        heartRate = (int) (Math.random() * (HEART_RATE_MAXIMUM - HEART_RATE_MINIMUM + 1) + HEART_RATE_MINIMUM);
    }

    private void updateViews() {
        stressView.setText(Integer.toString(stressLevel));
        alleviatedView.setText(Integer.toString(alleviatedTime));
        heartRateView.setText(Integer.toString(heartRate));
        updateProgressBar();
        setButtonListeners();
    }

    private void updateProgressBar() {
        new Thread(() -> {
            try {
                Thread.sleep(600);
                for (int i = 0 ; i < 100 ; i++) {
                    float percentage = (float) (i + 1) / 100;
                    runOnUiThread(() -> stressGauge.setValue((int) (stressLevel * percentage)));
                    Thread.sleep((long) ((float) (GAUGE_ANIMATION_TIME * stressLevel) / 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setButtonListeners() {
        vitalsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, VitalsActivity.class));
        });
        alleviateButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AlleviateActivity.class));
        });
        reliefButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ReliefActivity.class));
        });
    }
}