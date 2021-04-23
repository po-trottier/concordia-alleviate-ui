package com.concordia.alleviate.ui.settings;

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

public class SettingsFragment extends Fragment {

    private SettingsViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(SettingsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        return root;
    }
}