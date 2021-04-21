package com.concordia.alleviate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;
import com.concordia.alleviate.R;
import com.concordia.alleviate.adapters.ReliefAdapter;

import java.util.Arrays;

public class ReliefActivity extends WearableActivity {

    private static final String[] BREATHING_EXERCISES = {
        "Pursed lip breathing",
        "Diaphragmatic breathing",
        "Breath focus technique",
        "Lion’s breath",
        "Alternate nostril breathing",
        "Equal breathing",
        "Resonant or coherent breathing",
        "Sitali breath",
        "Deep breathing",
        "Humming bee breath (bhramari)",
    };

    WearableRecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relief);

        ReliefAdapter adapter = new ReliefAdapter(
            Arrays.asList(BREATHING_EXERCISES),
            i -> {
                Intent intent = new Intent(this, AlleviateActivity.class);
                intent.putExtra("exercise", BREATHING_EXERCISES[i]);
                this.startActivity(intent);
            });

        listView = findViewById(R.id.relief_list);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new WearableLinearLayoutManager(this));
        listView.setEdgeItemsCenteringEnabled(true);
        listView.setCircularScrollingGestureEnabled(true);
        listView.setBezelFraction(0.5f);
        listView.setScrollDegreesPerScreen(90);
    }
}