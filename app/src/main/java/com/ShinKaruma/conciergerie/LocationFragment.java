package com.ShinKaruma.conciergerie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ShinKaruma.conciergerie.cardAdapters.PlanningAdapter;
import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.pojo.PlanningDay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationFragment extends Fragment {

    private RecyclerView recyclerPlanning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        recyclerPlanning = view.findViewById(R.id.recyclerPlanning);
        recyclerPlanning.setLayoutManager(new LinearLayoutManager(getContext()));

        APIInterface api = APIClient.createApi(getContext());

        api.getPlanning().enqueue(new Callback<List<PlanningDay>>() {
            @Override
            public void onResponse(Call<List<PlanningDay>> call, Response<List<PlanningDay>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    PlanningAdapter adapter =
                            new PlanningAdapter(response.body(), getContext());

                    recyclerPlanning.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PlanningDay>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur planning", Toast.LENGTH_SHORT).show();
            }
        });
    }
}