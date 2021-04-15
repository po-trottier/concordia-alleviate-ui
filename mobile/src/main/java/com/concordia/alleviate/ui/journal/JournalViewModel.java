package com.concordia.alleviate.ui.journal;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JournalViewModel extends ViewModel {

    private final int STRESS_MINIMUM = 10;
    private final int STRESS_MAXIMUM = 95;
    private final int ALEVIATED_MINIMUM = 1;
    private final int ALEVIATED_MAXIMUM = 14;
    private final int HEART_MINIMUM = 60;
    private final int HEART_MAXIMUM = 120;
    private final int PRESSURE_TOP_MINIMUM = 110;
    private final int PRESSURE_TOP_MAXIMUM = 130;
    private final int PRESSURE_BOTTOM_MINIMUM = 60;
    private final int PRESSURE_BOTTOM_MAXIMUM = 90;
    private final int AGITATION_MINIMUM = 1;
    private final int AGITATION_MAXIMUM = 99;

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
        setAlleviatedTime((int) (Math.random() * (ALEVIATED_MAXIMUM - ALEVIATED_MINIMUM + 1) + ALEVIATED_MINIMUM));
        setHeartRate((int) (Math.random() * (HEART_MAXIMUM - HEART_MINIMUM + 1) + HEART_MINIMUM));
        setBloodPressureTop((int) (Math.random() * (PRESSURE_TOP_MAXIMUM - PRESSURE_TOP_MINIMUM + 1) + PRESSURE_TOP_MINIMUM));
        setBloodPressureBottom((int) (Math.random() * (PRESSURE_BOTTOM_MAXIMUM - PRESSURE_BOTTOM_MINIMUM + 1) + PRESSURE_BOTTOM_MINIMUM));
        setAgitationLevel((int) (Math.random() * (AGITATION_MAXIMUM - AGITATION_MINIMUM + 1) + AGITATION_MINIMUM));
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