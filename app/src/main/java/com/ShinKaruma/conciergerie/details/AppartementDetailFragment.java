package com.ShinKaruma.conciergerie.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.cardAdapters.LocationAdapter;
import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.pojo.Appartement;
import com.ShinKaruma.conciergerie.pojo.Location;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppartementDetailFragment extends Fragment {

    private int appartementId;

    private TextView tvNomAppartement, tvAdresseAppartement, tvProprietaire;
    private MaterialCardView cardLocationActive;
    private TextView tvLocataire, tvDatesLocation, tvStatutLocation;
    private RecyclerView recyclerLocations;

    // On récupère l'id de l'appartement depuis les arguments
    public static AppartementDetailFragment newInstance(int id) {
        AppartementDetailFragment fragment = new AppartementDetailFragment();
        Bundle args = new Bundle();
        args.putInt("appartement_id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appartement_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Lier les vues ---
        tvNomAppartement = view.findViewById(R.id.tvNomAppartement);
        tvAdresseAppartement = view.findViewById(R.id.tvAdresseAppartement);
        tvProprietaire = view.findViewById(R.id.tvProprietaire);

        cardLocationActive = view.findViewById(R.id.cardLocationActive);
        tvLocataire = view.findViewById(R.id.tvLocataire);
        tvDatesLocation = view.findViewById(R.id.tvDatesLocation);

        recyclerLocations = view.findViewById(R.id.recyclerLocations);
        recyclerLocations.setLayoutManager(new LinearLayoutManager(getContext()));

        // --- Récupérer l'id depuis les arguments ---
        if (getArguments() != null) {
            Log.e("AppartementDetailFragment", "je check ici"+getArguments().getInt("idAppartement_id", -1));
            appartementId = getArguments().getInt("idAppartement", -1);
            if (appartementId != -1) {
                loadAppartementDetails(appartementId);
            }
        }
    }

    private void loadAppartementDetails(int id) {
        APIInterface api = APIClient.createApi(getContext());

        api.getAppartementById(id).enqueue(new Callback<Appartement>() {
            @Override
            public void onResponse(Call<Appartement> call, Response<Appartement> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Appartement appartement = response.body();

                    // --- Remplir infos appartement ---
                    tvNomAppartement.setText(appartement.getNom());
                    tvAdresseAppartement.setText(appartement.getLieu() + " n°" + appartement.getNumero());
                    tvProprietaire.setText(appartement.getProprietaire().getUser().getPrenom()
                            + " " + appartement.getProprietaire().getUser().getNom());

                    // --- Location active ---
                    Location locationActive = appartement.getLocationActive(); // suppose que tu as isActive() ou getLocationActive()
                    if (locationActive != null) {
                        cardLocationActive.setVisibility(View.VISIBLE);
                        if (locationActive.getLocataire() != null) {
                            tvLocataire.setText(locationActive.getLocataire().getPrenom() + " "
                                    + locationActive.getLocataire().getNom());
                        }else{
                            tvLocataire.setText(getString(
                                    R.string.no_locataire_name
                            ));
                        }
                        tvDatesLocation.setText(locationActive.getDateDebut() + " - " + locationActive.getDateFin());
                    } else {
                        cardLocationActive.setVisibility(View.GONE);
                    }

                    // --- Historique locations ---
                    List<Location> locations = appartement.getLocations();
                    LocationAdapter adapter = new LocationAdapter(locations);
                    recyclerLocations.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "Erreur lors du chargement de l'appartement", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Appartement> call, Throwable t) {
                Toast.makeText(getContext(), "Impossible de joindre le serveur", Toast.LENGTH_SHORT).show();
            }
        });
    }
}