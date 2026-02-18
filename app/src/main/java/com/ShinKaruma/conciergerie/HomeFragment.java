package com.ShinKaruma.conciergerie;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ShinKaruma.conciergerie.adders.AddAppartmentActivity;
import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.network.SessionManager;
import com.ShinKaruma.conciergerie.pojo.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private MaterialToolbar toolbar;

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.homeToolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        ViewPager2 viewPager = view.findViewById(R.id.homeViewPager);
        TabLayout tabLayout = view.findViewById(R.id.homeTabLayout);

        MainPagerAdapter adapter = new MainPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(getString(R.string.home_calendar));
                            break;
                        case 1:
                            tab.setText(getString(R.string.home_rentals));
                            break;
                        case 2:
                            tab.setText(getString(R.string.home_apartments));
                            break;
                    }
                }).attach();

        loadUserInfo();
    }

    private void loadUserInfo() {
        APIInterface api = APIClient.createApi(requireContext());

        api.getMe().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() == null) return;

                SessionManager session =
                        SessionManager.getInstance(requireContext());
                session.setCurrentUser(response.body());

                toolbar.setTitle(
                        response.body().getConciergerie().getNom()
                                + " | "
                                + response.body().getType().toLowerCase()
                );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(requireContext(),
                        "Erreur chargement utilisateur",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ----- MENU -----

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu,
                                    @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_appartment_add) {
            openAddAppartement();
            return true;

        } else if (id == R.id.action_rental_add) {
            openAddRental();
            return true;

        } else if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Toast.makeText(requireContext(),
                getString(R.string.logout_success),
                Toast.LENGTH_SHORT).show();

        SessionManager sessionManager =
                SessionManager.getInstance(requireContext());
        sessionManager.clear();

        Intent intent = new Intent(requireContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openAddAppartement() {
        Intent intent = new Intent(requireContext(), AddAppartmentActivity.class);
        startActivity(intent);
    }

    private void openAddRental() {
        Toast.makeText(requireContext(),
                "Ajouter une location",
                Toast.LENGTH_SHORT).show();
    }
}