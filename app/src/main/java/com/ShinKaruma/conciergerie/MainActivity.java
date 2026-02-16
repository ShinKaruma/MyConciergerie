package com.ShinKaruma.conciergerie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialToolbar toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);

        APIInterface api = APIClient.createApi(getApplicationContext());
        api.getMe().enqueue(new Callback<User>(){

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                SessionManager session = SessionManager.getInstance(MainActivity.this);
                session.setCurrentUser(response.body());

                toolbar.setTitle(response.body().getConciergerie().getNom() + "   |   " + response.body().getType().toLowerCase());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }


        });

        ViewPager2 viewPager = findViewById(R.id.homeViewPager);
        TabLayout tabLayout = findViewById(R.id.homeTabLayout);

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
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


    private void logout(){
        Toast.makeText(this, this.getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
        SessionManager sessionManager = SessionManager.getInstance(this);
        sessionManager.clear();
        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openAddAppartement(){
        Intent intent = new Intent(this, AddAppartmentActivity.class);
        startActivity(intent);
    }

    private void openAddRental() {
        Toast.makeText(this, "Ajouter un location", Toast.LENGTH_SHORT).show();
    }

}