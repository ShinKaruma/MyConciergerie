package com.ShinKaruma.conciergerie.cardAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.pojo.Appartement;

import java.util.List;

public class AppartementAdapter extends RecyclerView.Adapter<AppartementAdapter.ViewHolder> {
    private List<Appartement> appartements;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Appartement appartement);
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
        holder.lieu.setText(appart.getLieu());
        holder.nomProprio.setText(appart.getProprietaire().toString());



        if (appart.isOccupe()) {
            holder.status.setText("🔴 En location");
        } else {
            holder.status.setText("🟢 Libre");
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(appart));
    }

    @Override
    public int getItemCount() {
        return appartements.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom, lieu, status, nomProprio;

        ViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.tvNom);
            nomProprio = itemView.findViewById(R.id.tvNomProprio);
            lieu = itemView.findViewById(R.id.tvAdresse);
            status = itemView.findViewById(R.id.tvStatut);
        }
    }
}
