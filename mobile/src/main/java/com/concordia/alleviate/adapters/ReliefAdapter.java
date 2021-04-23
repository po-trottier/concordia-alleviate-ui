package com.concordia.alleviate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.concordia.alleviate.R;
import com.concordia.alleviate.adapters.ReliefCard;

import java.util.ArrayList;

public class ReliefAdapter extends ArrayAdapter<ReliefCard> {
    public ReliefAdapter(Context context, ArrayList<ReliefCard> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ReliefCard card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_relief, parent, false);
        }
        // Lookup view for data population
        TextView cardTitle = convertView.findViewById(R.id.relief_card_title);
        // Populate the data into the template view using the data object
        cardTitle.setText(card.name);
        // Return the completed view to render on screen
        return convertView;
    }
}