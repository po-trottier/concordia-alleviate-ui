package com.concordia.alleviate.ui.journal;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.concordia.alleviate.R;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class JournalFragment extends Fragment {

    private final int ANIMATION_TIME = 5;

    private Activity activity;
    private JournalViewModel vm;

    private CustomGauge stressGauge;
    private TextView stressView;
    private TextView alleviatedView;
    private TextView heartRateView;
    private TextView bloodPressureTopView;
    private TextView bloodPressureBottomView;
    private TextView agitationView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        vm = new ViewModelProvider(this).get(JournalViewModel.class);

        View root = inflater.inflate(R.layout.fragment_journal, container, false);
        findViews(root);

        vm.generateRandomData();
        updateViewsData();

        return root;
    }

    private void findViews(View root) {
        stressGauge = root.findViewById(R.id.journal_progress_bar);
        stressView = root.findViewById(R.id.journal_stress_text);
        alleviatedView = root.findViewById(R.id.journal_alleviated_text);
        heartRateView = root.findViewById(R.id.journal_heart_text);
        bloodPressureTopView = root.findViewById(R.id.journal_pressure_top_text);
        bloodPressureBottomView = root.findViewById(R.id.journal_pressure_bottom_text);
        agitationView = root.findViewById(R.id.journal_agitation_text);
    }

    private void updateViewsData() {
        vm.getAlleviatedTime().observe(getViewLifecycleOwner(), (i) -> alleviatedView.setText(Integer.toString(i)));
        vm.getHeartRate().observe(getViewLifecycleOwner(), (i) -> heartRateView.setText(Integer.toString(i)));
        vm.getBloodPressureTop().observe(getViewLifecycleOwner(), (i) -> bloodPressureTopView.setText(Integer.toString(i)));
        vm.getBloodPressureBottom().observe(getViewLifecycleOwner(), (i) -> bloodPressureBottomView.setText("/ " + i));
        vm.getAgitationLevel().observe(getViewLifecycleOwner(), (i) -> agitationView.setText(Integer.toString(i)));
        vm.getStressLevel().observe(getViewLifecycleOwner(), (i) -> {
            stressView.setText(Integer.toString(i));
            updateProgressBar(i);
        });
    }

    private void updateProgressBar(int stressValue) {
        new Thread(() -> {
            try {
                Thread.sleep(600);
                for (int i = 0 ; i < 100 ; i++) {
                    float percentage = (float) (i + 1) / 100;
                    activity.runOnUiThread(() -> stressGauge.setValue((int) (stressValue * percentage)));
                    Thread.sleep((long) ((float) (ANIMATION_TIME * stressValue) / 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}