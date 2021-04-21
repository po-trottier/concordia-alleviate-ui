package com.concordia.alleviate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import com.concordia.alleviate.R;

public class AlleviateActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alleviate);
        setIntentExercise();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setIntentExercise();
    }

    private void setIntentExercise() {
        // TODO Change this to fit the design
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String selectedExercise = intent.getExtras().getString("exercise");
            if (selectedExercise != null && selectedExercise.length() > 0) {
                TextView demoTextView = findViewById(R.id.alleviate_text);
                demoTextView.setText(selectedExercise);
            }
        }
    }
}