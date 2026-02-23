package com.ShinKaruma.conciergerie.cardAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.pojo.PlanningDay;
import com.ShinKaruma.conciergerie.pojo.PlanningEvent;

import java.util.List;

public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.DayViewHolder> {

    private List<PlanningDay> days;
    private Context context;

    public PlanningAdapter(List<PlanningDay> days, Context context) {
        this.days = days;
        this.context = context;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_planning_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {

        PlanningDay day = days.get(position);
        holder.tvDayTitle.setText(day.getDate());

        holder.containerEvents.removeAllViews();

        for (PlanningEvent event : day.getEvents()) {

            View eventView = LayoutInflater.from(context)
                    .inflate(R.layout.item_planning_event, holder.containerEvents, false);

            TextView tvType = eventView.findViewById(R.id.tvType);
            TextView tvAppartement = eventView.findViewById(R.id.tvAppartement);
            TextView tvLocataire = eventView.findViewById(R.id.tvLocataire);

            tvType.setText(
                    event.getType().equals("ARRIVEE")
                            ? "Arrivée"
                            : "Départ"
            );

            tvAppartement.setText(event.getLocation().getAppartement().getNom());
            if (event.getLocation().getLocataire() != null) {
                tvLocataire.setText(event.getLocation().getLocataire().toString());
            }else{

                tvLocataire.setText(context.getString(R.string.no_locataire_name));
            }

            holder.containerEvents.addView(eventView);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {

        TextView tvDayTitle;
        LinearLayout containerEvents;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayTitle = itemView.findViewById(R.id.tvDayTitle);
            containerEvents = itemView.findViewById(R.id.containerEvents);
        }
    }
}
