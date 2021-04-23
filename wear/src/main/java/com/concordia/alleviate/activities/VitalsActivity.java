package com.concordia.alleviate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.concordia.alleviate.R;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class VitalsActivity extends WearableActivity {

    private static final int HEART_RATE_MINIMUM = 60;
    private static final int HEART_RATE_MAXIMUM = 140;
    private static final int AGITATION_MINIMUM = 10;
    private static final int AGITATION_MAXIMUM = 60;

    private int heartRate;
    private int agitationLevel;

    private TextView vitalsViewHeartRate;
    private TextView vitalsViewBloodPressure;
    private TextView vitalsViewAgitationLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

        findViews();
        generateRandomData();
        updateViews();
    }

    private void findViews() {
        vitalsViewHeartRate = findViewById(R.id.vitals_heart_rate_text);
        vitalsViewBloodPressure = findViewById(R.id.vitals_blood_pressure_text);
        vitalsViewAgitationLevel = findViewById(R.id.vitals_agitation_level_text);
    }

    private void generateRandomData() {
        heartRate = (int) (Math.random() * (HEART_RATE_MAXIMUM - HEART_RATE_MINIMUM + 1) + HEART_RATE_MINIMUM);
        agitationLevel = (int) (Math.random() * (AGITATION_MAXIMUM - AGITATION_MINIMUM + 1) + AGITATION_MINIMUM);
    }

    private void updateViews() {
        vitalsViewHeartRate.setText(Integer.toString(heartRate));
        vitalsViewBloodPressure.setText("120 / 80");
        vitalsViewAgitationLevel.setText(Integer.toString(agitationLevel));

    }
}