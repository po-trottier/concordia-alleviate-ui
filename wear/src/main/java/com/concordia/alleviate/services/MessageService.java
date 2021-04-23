package com.concordia.alleviate.services;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.concordia.alleviate.activities.AlleviateActivity;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class MessageService extends WearableListenerService {

    @Override
    public void onMessageReceived(@NonNull MessageEvent event) {
        if (event.getPath().equalsIgnoreCase("/alleviate/now")) {
            Intent intent = new Intent(this, AlleviateActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (event.getData() != null && event.getData().length > 0)
                intent.putExtra("exercise", new String(event.getData(), StandardCharsets.UTF_8));
            startActivity(intent);
        } else {
            super.onMessageReceived(event);
        }
    }
}
