package com.concordia.alleviate.ui.relief;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReliefViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReliefViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}