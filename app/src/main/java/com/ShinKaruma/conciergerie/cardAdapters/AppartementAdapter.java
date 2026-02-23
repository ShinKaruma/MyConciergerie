package com.ShinKaruma.conciergerie.cardAdapters;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.pojo.Appartement;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AppartementAdapter extends RecyclerView.Adapter<AppartementAdapter.ViewHolder> {
    private List<Appartement> appartements;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Appartement appartement, View sharedView);
    }

    public AppartementAdapter(List<Appartement> appartements, OnItemClickListener listener) {
        this.appartements = appartements;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appartement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Appartement appart = appartements.get(position);


        holder.nom.setText(appart.getNom());
        holder.lieu.setText(appart.getAdresse());
        holder.nomProprio.setText(appart.getProprietaire().toString());
        String kits = holder.itemView.getContext().getString(R.string.kit_count) + appart.getNbKitsDispo() ;

        holder.kits.setText(kits);




        if (appart.isOccupe()) {
            holder.status.setText(R.string.appartement_status_rented);
        } else {
            holder.status.setText(R.string.appartement_status_free);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(appart, holder.card));
    }

    @Override
    public int getItemCount() {
        return appartements.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom, lieu, status, nomProprio, kits;
        MaterialCardView card;


        ViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardAppartement);
            nom = itemView.findViewById(R.id.tvNom);
            nomProprio = itemView.findViewById(R.id.tvNomProprio);
            lieu = itemView.findViewById(R.id.tvAdresse);
            kits = itemView.findViewById(R.id.tvKits);
            status = itemView.findViewById(R.id.tvStatut);
        }
    }
}
