package com.concordia.alleviate.ui.journal;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class JournalViewModel extends ViewModel {

    private static final int STRESS_MINIMUM = 10;
    private static final int STRESS_MAXIMUM = 95;
    private static final int ALLEVIATED_MINIMUM = 1;
    private static final int ALLEVIATED_MAXIMUM = 14;
    private static final int HEART_MINIMUM = 60;
    private static final int HEART_MAXIMUM = 120;
    private static final int PRESSURE_TOP_MINIMUM = 110;
    private static final int PRESSURE_TOP_MAXIMUM = 130;
    private static final int PRESSURE_BOTTOM_MINIMUM = 60;
    private static final int PRESSURE_BOTTOM_MAXIMUM = 90;
    private static final int AGITATION_MINIMUM = 1;
    private static final int AGITATION_MAXIMUM = 99;

    private final MutableLiveData<Integer> stressLevel;
    private final MutableLiveData<Integer> alleviatedTime;
    private final MutableLiveData<Integer> heartRate;
    private final MutableLiveData<Integer> bloodPressureTop;
    private final MutableLiveData<Integer> bloodPressureBottom;
    private final MutableLiveData<Integer> agitationLevel;

    public JournalViewModel() {
        stressLevel = new MutableLiveData<>();
        stressLevel.setValue(0);

        alleviatedTime = new MutableLiveData<>();
        alleviatedTime.setValue(0);

        heartRate = new MutableLiveData<>();
        heartRate.setValue(0);

        bloodPressureTop = new MutableLiveData<>();
        bloodPressureTop.setValue(0);

        bloodPressureBottom = new MutableLiveData<>();
        bloodPressureBottom.setValue(0);

        agitationLevel = new MutableLiveData<>();
        agitationLevel.setValue(0);
    }

    public void generateRandomData() {
        setStressLevel((int) (Math.random() * (STRESS_MAXIMUM - STRESS_MINIMUM + 1) + STRESS_MINIMUM));
        setAlleviatedTime((int) (Math.random() * (ALLEVIATED_MAXIMUM - ALLEVIATED_MINIMUM + 1) + ALLEVIATED_MINIMUM));
        setHeartRate((int) (Math.random() * (HEART_MAXIMUM - HEART_MINIMUM + 1) + HEART_MINIMUM));
        setBloodPressureTop((int) (Math.random() * (PRESSURE_TOP_MAXIMUM - PRESSURE_TOP_MINIMUM + 1) + PRESSURE_TOP_MINIMUM));
        setBloodPressureBottom((int) (Math.random() * (PRESSURE_BOTTOM_MAXIMUM - PRESSURE_BOTTOM_MINIMUM + 1) + PRESSURE_BOTTOM_MINIMUM));
        setAgitationLevel((int) (Math.random() * (AGITATION_MAXIMUM - AGITATION_MINIMUM + 1) + AGITATION_MINIMUM));
    }

    public BarData getHeartRateChartData(int color) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            float random = (float) (Math.random() * (HEART_MAXIMUM - HEART_MINIMUM + 1) + HEART_MINIMUM);
            entries.add(new BarEntry(i, random));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Heart Rate");
        dataSet.setColor(color);
        dataSet.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.1f);
        return data;
    }

    public BarData getAgitationChartData(int color) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            float random = (float) (Math.random() * (AGITATION_MAXIMUM - AGITATION_MINIMUM + 1) + AGITATION_MINIMUM);
            entries.add(new BarEntry(i, random));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Agitation");
        dataSet.setColor(color);
        dataSet.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.1f);
        return data;
    }

    public MutableLiveData<Integer> getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel.setValue(stressLevel);
    }

    public MutableLiveData<Integer> getAlleviatedTime() {
        return alleviatedTime;
    }

    public void setAlleviatedTime(int alleviatedTime) {
        this.alleviatedTime.setValue(alleviatedTime);
    }

    public MutableLiveData<Integer> getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate.setValue(heartRate);
    }

    public MutableLiveData<Integer> getBloodPressureTop() {
        return bloodPressureTop;
    }

    public void setBloodPressureTop(int bloodPressureTop) {
        this.bloodPressureTop.setValue(bloodPressureTop);
    }

    public MutableLiveData<Integer> getBloodPressureBottom() {
        return bloodPressureBottom;
    }

    public void setBloodPressureBottom(int bloodPressureBottom) {
        this.bloodPressureBottom.setValue(bloodPressureBottom);
    }

    public MutableLiveData<Integer> getAgitationLevel() {
        return agitationLevel;
    }

    public void setAgitationLevel(int agitationLevel) {
        this.agitationLevel.setValue(agitationLevel);
    }
}