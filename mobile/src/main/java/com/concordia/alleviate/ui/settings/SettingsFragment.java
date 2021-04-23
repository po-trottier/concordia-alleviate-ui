package com.concordia.alleviate.ui.settings;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.concordia.alleviate.R;
import com.concordia.alleviate.models.ReliefExercise;
import com.concordia.alleviate.ui.relief.ReliefViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SettingsFragment extends Fragment {

    private View root;
    private ReliefViewModel reliefVM;
    private SettingsViewModel settingsVM;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reliefVM = new ViewModelProvider(this).get(ReliefViewModel.class);
        settingsVM = new ViewModelProvider(this).get(SettingsViewModel.class);

        root = inflater.inflate(R.layout.fragment_settings, container, false);

        updateViewData();
        setViewListeners(inflater);

        return root;
    }

    public void updateViewData() {
        TextView age = root.findViewById(R.id.settings_age_text);
        settingsVM.getAge().observe(getViewLifecycleOwner(), (i) -> age.setText(i + " years old"));

        TextView sex = root.findViewById(R.id.settings_sex_text);
        settingsVM.getSex().observe(getViewLifecycleOwner(), sex::setText);

        TextView height = root.findViewById(R.id.settings_height_text);
        settingsVM.getHeightString().observe(getViewLifecycleOwner(), height::setText);

        TextView weight = root.findViewById(R.id.settings_weight_text);
        settingsVM.getWeight().observe(getViewLifecycleOwner(), (i) -> weight.setText(i + " lbs"));

        TextView relief = root.findViewById(R.id.settings_relief_method_text);
        settingsVM.getRelief().observe(getViewLifecycleOwner(), relief::setText);

        TextView contact = root.findViewById(R.id.settings_emergency_contact_text);
        settingsVM.getContact().observe(getViewLifecycleOwner(), contact::setText);
    }

    private void setViewListeners(LayoutInflater inflater) {
        LinearLayout age = root.findViewById(R.id.settings_age);
        age.setOnClickListener(v -> openAgeModal(inflater));

        LinearLayout sex = root.findViewById(R.id.settings_sex);
        sex.setOnClickListener(v -> openSexModal(inflater));

        LinearLayout height = root.findViewById(R.id.settings_height);
        height.setOnClickListener(v -> openHeightModal(inflater));

        LinearLayout weight = root.findViewById(R.id.settings_weight);
        weight.setOnClickListener(v -> openWeightModal(inflater));

        LinearLayout relief = root.findViewById(R.id.settings_relief_method);
        relief.setOnClickListener(v -> openReliefModal(inflater));

        LinearLayout contact = root.findViewById(R.id.settings_emergency_contact);
        contact.setOnClickListener(v -> openContactModal(inflater));
    }

    private void openAgeModal(LayoutInflater inflater) {
        AtomicReference<LocalDate> birthday = new AtomicReference<>(settingsVM.getBirthday());

        View view = inflater.inflate(R.layout.dialog_age, null, false);
        EditText ageView = view.findViewById(R.id.dialog_age);

        DatePickerDialog dialog = new DatePickerDialog(
            getContext(),
            R.style.Theme_Alleviate_DatePicker,
            (innerView, year, month, dayOfMonth) -> {
                birthday.set(LocalDate.of(year, month + 1, dayOfMonth));
                ageView.setText(birthday.get().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            },
            birthday.get().getYear(),
            birthday.get().getMonthValue() - 1,
            birthday.get().getDayOfMonth()
        );

        ageView.setText(birthday.get().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        ageView.setOnClickListener(v -> dialog.show());

        showModal(R.string.settings_age_modal, view, (d, w) -> {
            settingsVM.setBirthday(birthday.get());
            updateViewData();
        });
    }

    private void openSexModal(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_sex, null, false);
        Spinner spinner = view.findViewById(R.id.dialog_sex);

        ArrayList<String> options = new ArrayList<>(Arrays.asList("Male", "Female", "Other"));
        spinner.setAdapter(new ArrayAdapter<>(getContext(),R.layout.spinner_selection, options));
        spinner.setSelection(options.indexOf(settingsVM.getSex().getValue()));

        showModal(R.string.settings_sex_modal, view, (dialog, which) -> {
            settingsVM.setSex((String) spinner.getSelectedItem());
        });
    }

    private void openHeightModal(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_height, null, false);

        EditText heightFeetView = view.findViewById(R.id.dialog_height_feet);
        heightFeetView.setText(Integer.toString(settingsVM.getHeightFeet()));
        EditText heightInchesView = view.findViewById(R.id.dialog_height_inches);
        heightInchesView.setText(Integer.toString(settingsVM.getHeightInches()));

        showModal(R.string.settings_height_modal, view, (dialog, which) -> {
            int feet = Integer.parseInt(heightFeetView.getText().toString());
            int inches = Integer.parseInt(heightInchesView.getText().toString());
            settingsVM.setHeight(feet, Math.min(inches, 11));
        });
    }

    private void openWeightModal(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_weight, null, false);
        EditText weightView = view.findViewById(R.id.dialog_weight);
        weightView.setText(Float.toString(settingsVM.getWeight().getValue()));

        showModal(R.string.settings_weight_modal, view, (dialog, which) -> {
            settingsVM.setWeight(Float.parseFloat(weightView.getText().toString()));
        });
    }

    private void openReliefModal(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_relief, null, false);
        Spinner spinner = view.findViewById(R.id.dialog_relief);

        ArrayList<String> options = Arrays.stream(reliefVM.getExercises())
            .map(ReliefExercise::getTitle)
            .collect(Collectors.toCollection(ArrayList::new));
        spinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.spinner_selection, options));
        spinner.setSelection(options.indexOf(settingsVM.getRelief().getValue()));

        showModal(R.string.settings_relief_method_modal, view, (dialog, which) -> {
            settingsVM.setRelief((String) spinner.getSelectedItem());
        });
    }

    private void openContactModal(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_contact, null, false);
        Spinner spinner = view.findViewById(R.id.dialog_contact);

        ArrayList<String> options = new ArrayList<>(Arrays.asList("Mom", "Dad", "Jeremy", "Kayla"));
        spinner.setAdapter(new ArrayAdapter<>(getContext(),R.layout.spinner_selection, options));
        spinner.setSelection(options.indexOf(settingsVM.getContact().getValue()));

        showModal(R.string.settings_emergency_contact_modal, view, (dialog, which) -> {
            settingsVM.setContact((String) spinner.getSelectedItem());
        });
    }

    private void showModal(int title, View view, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(title)
            .setView(view)
            .setPositiveButton(R.string.settings_modal_set, listener)
            .setNegativeButton(R.string.settings_modal_cancel, null);
        builder.create().show();
    }
}