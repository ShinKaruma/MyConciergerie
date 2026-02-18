package com.ShinKaruma.conciergerie.cardAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.pojo.Location;

import java.util.Date;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private final List<Location> locations;

    public LocationAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        Context context = holder.tvLocataire.getContext();


        if (location.getLocataire() != null) {
            holder.tvLocataire.setText(location.getLocataire().getPrenom() + " "
                    + location.getLocataire().getNom());
        }else{

            holder.tvLocataire.setText(context.getString(R.string.no_locataire_name));
        }

        holder.tvDates.setText(location.getDateDebut() + " - " + location.getDateFin());


        Date now = new Date();
        String statut;
        if (location.isActive()) {
            statut = context.getString(R.string.location_status_active);
        } else if (now.after(location.getDateDebut())) { // futur
            statut = context.getString(R.string.location_status_future);
        } else {
            statut = context.getString(R.string.location_status_past);
        }
        holder.tvStatut.setText(statut);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocataire, tvDates, tvStatut;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocataire = itemView.findViewById(R.id.tvLocataire);
            tvDates = itemView.findViewById(R.id.tvDates);
            tvStatut = itemView.findViewById(R.id.tvStatut);
        }
    }
}