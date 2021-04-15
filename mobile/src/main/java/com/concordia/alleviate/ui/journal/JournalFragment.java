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
import com.concordia.alleviate.formatters.DayAxisFormatter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class JournalFragment extends Fragment {

    private final int GAUGE_ANIMATION_TIME = 5;
    private final int CHART_ANIMATION_TIME = 2;

    private Activity activity;
    private JournalViewModel vm;

    private BarChart heartRateChart;
    private BarChart agitationChart;
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

        View rootLayout = inflater.inflate(R.layout.fragment_journal, container, false);
        findViews(rootLayout);

        vm.generateRandomData();
        updateChart(heartRateChart);
        updateChart(agitationChart);
        updateViewsData();

        return rootLayout;
    }

    private void findViews(View root) {
        heartRateChart = root.findViewById(R.id.journal_heart_graph);
        agitationChart = root.findViewById(R.id.journal_agitation_graph);
        stressGauge = root.findViewById(R.id.journal_progress_bar);
        stressView = root.findViewById(R.id.journal_stress_text);
        alleviatedView = root.findViewById(R.id.journal_alleviated_text);
        heartRateView = root.findViewById(R.id.journal_heart_text);
        bloodPressureTopView = root.findViewById(R.id.journal_pressure_top_text);
        bloodPressureBottomView = root.findViewById(R.id.journal_pressure_bottom_text);
        agitationView = root.findViewById(R.id.journal_agitation_text);
    }

    private void updateViewsData() {
        heartRateChart.setData(vm.getHeartRateChartData(activity.getColor(R.color.turquoise)));
        agitationChart.setData(vm.getAgitationChartData(activity.getColor(R.color.yellow_orange)));
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

    private void updateChart(BarChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(activity.getColor(R.color.dark));
        xAxis.setValueFormatter(new DayAxisFormatter(getContext()));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setLabelCount(5, true);
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setTextColor(activity.getColor(R.color.dark));
        rightAxis.setGridColor(activity.getColor(R.color.grey));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        Description description = chart.getDescription();
        description.setEnabled(false);

        chart.setTouchEnabled(false);
        chart.animateXY(CHART_ANIMATION_TIME * 1000, CHART_ANIMATION_TIME * 1000, Easing.EaseOutCubic);
    }

    private void updateProgressBar(int stressValue) {
        new Thread(() -> {
            try {
                Thread.sleep(600);
                for (int i = 0 ; i < 100 ; i++) {
                    float percentage = (float) (i + 1) / 100;
                    activity.runOnUiThread(() -> stressGauge.setValue((int) (stressValue * percentage)));
                    Thread.sleep((long) ((float) (GAUGE_ANIMATION_TIME * stressValue) / 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}