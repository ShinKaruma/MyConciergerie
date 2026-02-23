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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.cardAdapters.LocationAdapter;
import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.pojo.Appartement;
import com.ShinKaruma.conciergerie.pojo.Location;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppartementDetailFragment extends Fragment {

    private int appartementId;
    private ShimmerFrameLayout shimmerLayout;
    private View contentLayout;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appartement_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.detailToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        contentLayout = view.findViewById(R.id.scrollDetailAppartement);

        shimmerLayout.startShimmer();




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
            appartementId = getArguments().getInt("idAppartement", -1);

            if (appartementId != -1) {
                toolbar.setTitle("Appartement #" + appartementId);
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
                    shimmerLayout.stopShimmer();
                    shimmerLayout.setVisibility(View.GONE);

                    contentLayout.setVisibility(View.VISIBLE);

                    Appartement appartement = response.body();

                    // --- Remplir infos appartement ---
                    tvNomAppartement.setText(appartement.getNom());
                    tvAdresseAppartement.setText(appartement.getAdresse());
                    tvProprietaire.setText(appartement.getProprietaire().toString());

                    // --- Location active ---
                    Location locationActive = appartement.getLocationActive(); // suppose que tu as isActive() ou getLocationActive()
                    if (locationActive != null) {
                        cardLocationActive.setVisibility(View.VISIBLE);
                        if (locationActive.getLocataire() != null) {
                            tvLocataire.setText(locationActive.getLocataire().toString());
                        }else{
                            tvLocataire.setText(getString(
                                    R.string.no_locataire_name
                            ));
                        }
                        tvDatesLocation.setText(locationActive.getDateDebutString() + " - " + locationActive.getDateFinString());
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