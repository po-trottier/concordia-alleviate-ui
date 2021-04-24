package com.concordia.alleviate.ui.settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.Period;

public class SettingsViewModel extends ViewModel {
    private LocalDate birthday;
    private int feet;
    private int inches;
    private final MutableLiveData<String> sex;
    private final MutableLiveData<Float> weight;
    private final MutableLiveData<String> height;
    private final MutableLiveData<String> relief;
    private final MutableLiveData<String> contact;

    public SettingsViewModel() {
        birthday = LocalDate.of(2000, 1, 1);

        sex = new MutableLiveData<>();
        sex.setValue("Male");

        weight = new MutableLiveData<>();
        weight.setValue(171.5f);

        height = new MutableLiveData<>();
        height.setValue("5\" 6'");
        feet = 5;
        inches = 6;

        relief = new MutableLiveData<>();
        relief.setValue("Lion’s breath");

        contact = new MutableLiveData<>();
        contact.setValue("Mom");
    }

    public MutableLiveData<Integer> getAge() {
        MutableLiveData<Integer> age = new MutableLiveData<>();
        age.setValue(Period.between(birthday, LocalDate.now()).getYears());
        return age;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setBirthday(LocalDate date) {
        this.birthday = date;
    }

    public MutableLiveData<String> getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.setValue(sex);
    }

    public MutableLiveData<Float> getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight.setValue(weight);
    }

    public MutableLiveData<String> getHeightString() {
        return height;
    }

    public int getHeightFeet() {
        return feet;
    }

    public int getHeightInches() {
        return inches;
    }

    public void setHeight(int feet, int inches) {
        this.height.setValue(feet + "\" " + inches + "'");
        this.feet = feet;
        this.inches = inches;
    }

    public MutableLiveData<String> getRelief() {
        return relief;
    }

    public void setRelief(String relief) {
        this.relief.setValue(relief);
    }

    public MutableLiveData<String> getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.setValue(contact);
    }
}