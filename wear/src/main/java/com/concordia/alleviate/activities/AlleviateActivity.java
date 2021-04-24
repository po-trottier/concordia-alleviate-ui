package com.concordia.alleviate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import com.concordia.alleviate.R;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class AlleviateActivity extends WearableActivity {

    private static final String DEFAULT_EXERCISE = "Lion’s breath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alleviate);
        setIntentExercise();
        startExercise();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setIntentExercise();
        startExercise();
    }

    private void setIntentExercise() {
        // Set default value
        TextView exerciseView = findViewById(R.id.alleviate_text);
        exerciseView.setText(DEFAULT_EXERCISE);
        // Override default value
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String selectedExercise = intent.getExtras().getString("exercise");
            if (selectedExercise != null && selectedExercise.length() > 0) {
                exerciseView.setText(selectedExercise);
            }
        }
    }

    private void startExercise() {
        CustomGauge exerciseGauge = findViewById(R.id.alleviate_progress_bar);
        TextView actionView = findViewById(R.id.alleviate_text_action);

        exerciseGauge.setValue(50);
        actionView.setText("Breath In");
    }
}