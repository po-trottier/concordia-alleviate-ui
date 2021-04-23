package com.concordia.alleviate.ui.relief;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.concordia.alleviate.R;
import com.concordia.alleviate.adapters.ReliefAdapter;
import com.concordia.alleviate.adapters.ReliefCard;

import java.util.ArrayList;

public class ReliefFragment extends Fragment {

    private ReliefViewModel vm;
    private ListView listView;
    private Context context;

    private static final String[] BREATHING_EXERCISES = {
            "Pursed lip breathing",
            "Diaphragmatic breathing",
            "Breath focus technique",
            "Lion’s breath",
            "Alternate nostril breathing",
            "Equal breathing",
            "Resonant or coherent breathing",
            "Sitali breath",
            "Deep breathing",
            "Humming bee breath (bhramari)",
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ReliefViewModel.class);
        context = getContext();

        View root = inflater.inflate(R.layout.fragment_relief, container, false);

        ArrayList<ReliefCard> arrayOfCards = new ArrayList<ReliefCard>();
        for(String s: BREATHING_EXERCISES){
            arrayOfCards.add(new ReliefCard(s, "BREATHING"));
        }
        ReliefAdapter reliefAdapter = new ReliefAdapter(context, arrayOfCards);
        listView = root.findViewById(R.id.relief_list);
        listView.setAdapter(reliefAdapter);

        return root;
    }
}