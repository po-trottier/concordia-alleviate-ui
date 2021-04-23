package com.concordia.alleviate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.concordia.alleviate.R;
import com.concordia.alleviate.models.OnItemClickListener;
import com.concordia.alleviate.models.ReliefExercise;

public class ReliefAdapter extends ArrayAdapter<ReliefExercise> {

    ReliefExercise[] exercises;
    OnItemClickListener listener;

    public ReliefAdapter(Context context, ReliefExercise[] exercises, OnItemClickListener listener) {
        super(context, 0, exercises);
        this.exercises = exercises;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReliefExercise exercise = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_relief, parent, false);

        TextView cardTitle = convertView.findViewById(R.id.relief_card_title);
        cardTitle.setText(exercise.getTitle());

        TextView cardDuration = convertView.findViewById(R.id.relief_card_duration);
        cardDuration.setText(exercise.getDuration() + " min");

        TextView cardDifficulty = convertView.findViewById(R.id.relief_card_difficulty);
        cardDifficulty.setText(exercise.getDifficulty());

        ImageButton cardButton = convertView.findViewById(R.id.relief_card_play_button);
        cardButton.setOnClickListener(v -> {
            listener.onClick(exercise);
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return exercises.length;
    }

    @Override
    public ReliefExercise getItem(int position) {
        return exercises[position];
    }

    public void setData(ReliefExercise[] data) {
        exercises = data;
        notifyDataSetChanged();
    }
}