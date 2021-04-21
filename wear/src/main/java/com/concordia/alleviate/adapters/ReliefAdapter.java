package com.concordia.alleviate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.concordia.alleviate.R;
import com.concordia.alleviate.interfaces.OnItemClickListener;

import java.util.List;

public class ReliefAdapter extends RecyclerView.Adapter<ReliefAdapter.ViewHolder> {

    private final List<String> reliefs;
    private final OnItemClickListener listener;

    public ReliefAdapter(List<String> reliefs, OnItemClickListener listener) {
        this.reliefs = reliefs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_relief, parent, false);
        ViewHolder holder = new ViewHolder(contactView);
        contactView.setOnClickListener(v -> {
            final int position = holder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onClick(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView positionView = holder.positionView;
        positionView.setText(String.format("%02d", position + 1) + ".");

        TextView titleView = holder.titleView;
        titleView.setText(reliefs.get(position));
    }

    @Override
    public int getItemCount() {
        return reliefs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView positionView;
        public TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            positionView = itemView.findViewById(R.id.adapter_relief_position);
            titleView = itemView.findViewById(R.id.adapter_relief_title);
        }
    }
}
