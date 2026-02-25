package com.ShinKaruma.conciergerie.cardAdapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.pojo.CalendarEvent;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class DayEventAdapter
        extends RecyclerView.Adapter<DayEventAdapter.ViewHolder> {

    private final List<CalendarEvent> events;

    public DayEventAdapter(List<CalendarEvent> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        CalendarEvent event = events.get(position);

        holder.tvAppartement.setText(event.appartementNom);
        holder.tvProprietaire.setText(event.proprietaireString);
        holder.tvDates.setText(
                event.start + " → " + event.end
        );

        // Couleur propriétaire en bordure
        holder.card.setStrokeColor(
                Color.parseColor(event.proprietaireColor)
        );
        holder.card.setStrokeWidth(4);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppartement;
        TextView tvProprietaire;
        TextView tvDates;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = (MaterialCardView) itemView;
            tvAppartement =
                    itemView.findViewById(R.id.tvAppartement);
            tvProprietaire =
                    itemView.findViewById(R.id.tvProprietaire);
            tvDates =
                    itemView.findViewById(R.id.tvDates);
        }
    }
}