package com.ShinKaruma.conciergerie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import com.ShinKaruma.conciergerie.cardAdapters.AppartementAdapter;
import com.ShinKaruma.conciergerie.details.AppartementDetailFragment;
import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.pojo.Appartement;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppartementsFragment extends Fragment {

    private ShimmerFrameLayout shimmerLayout;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appartements, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmerLayout = view.findViewById(R.id.shimmerLayout);

        shimmerLayout.startShimmer();


        recyclerView = view.findViewById(R.id.recyclerAppartements);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        APIInterface api = APIClient.createApi(getContext());

        api.getAppartements().enqueue(
                new Callback<List<Appartement>>(){

                    @Override
                    public void onResponse(Call<List<Appartement>> call, Response<List<Appartement>> response) {
                        if (response.isSuccessful()) {
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);

                            recyclerView.setVisibility(View.VISIBLE);

                            List<Appartement> appartements = response.body();

                            AppartementAdapter adapter = new AppartementAdapter(appartements, (appartement, sharedView) -> {

                                AppartementDetailFragment fragment = new AppartementDetailFragment();
                                String transitionName = "appartement_card_" + appartement.getId();
                                ViewCompat.setTransitionName(requireView(), transitionName);

                               Bundle bundle = new Bundle();
                               bundle.putInt("idAppartement", appartement.getId());
                               fragment.setArguments(bundle);

                                requireActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .setReorderingAllowed(true)
                                        .addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView))
                                        .replace(R.id.mainContainer, fragment)
                                        .addToBackStack(null)
                                        .commit();


                            });

                            recyclerView.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Appartement>> call, Throwable t) {
                        Toast.makeText(getContext(), getString(R.string.error_api_access), Toast.LENGTH_SHORT).show();
                    }
                }
        );



    }
}