package com.concordia.alleviate.activities;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import com.concordia.alleviate.R;

public class AlleviateActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alleviate);
    }
}