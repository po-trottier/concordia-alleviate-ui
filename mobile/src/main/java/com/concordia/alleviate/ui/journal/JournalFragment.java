package com.concordia.alleviate.ui.journal;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.concordia.alleviate.R;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class JournalFragment extends Fragment {

    private final int ANIMATION_TIME = 5;
    private final int PROGRESS_MINIMUM = 10;
    private final int PROGRESS_MAXIMUM = 95;

    private Activity activity;
    private JournalViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        vm = new ViewModelProvider(this).get(JournalViewModel.class);

        View root = inflater.inflate(R.layout.fragment_journal, container, false);

        int stressValue = (int) (Math.random() * (PROGRESS_MAXIMUM - PROGRESS_MINIMUM + 1) + PROGRESS_MINIMUM);

        CustomGauge gauge = root.findViewById(R.id.journal_progress_bar);
        new Thread(() -> {
            try {
                Thread.sleep(600);
                for (int i = 0 ; i < 100 ; i++) {
                    float percentage = (float) (i + 1) / 100;
                    activity.runOnUiThread(() -> gauge.setValue((int) (stressValue * percentage)));
                    Thread.sleep((long) ((float) (ANIMATION_TIME * stressValue) / 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        final TextView textView = root.findViewById(R.id.text_home);
        vm.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
}