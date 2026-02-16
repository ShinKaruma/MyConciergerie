package com.ShinKaruma.conciergerie.adders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ShinKaruma.conciergerie.R;
import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.network.SessionManager;
import com.ShinKaruma.conciergerie.pojo.Proprietaire;
import com.ShinKaruma.conciergerie.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAppartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_appartment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bouton retour
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        SessionManager session = SessionManager.getInstance(this);
        User currentUser = session.getCurrentUser();


        Spinner proprietaireSpinner = findViewById(R.id.proprietaireSpinner);

        APIInterface api = APIClient.createApi(getApplicationContext());
        Log.d("AddAppartmentActivity", "currentUser: " + currentUser.getType());

        if("PROPRIETAIRE".equals(currentUser.getType())){

            api.getProprietairesByUserId(currentUser.getId()).enqueue(
                    new Callback<List<Proprietaire>>(){

                        @Override
                        public void onResponse(Call<List<Proprietaire>> call, Response<List<Proprietaire>> response) {
                            if (response.isSuccessful()) {
                                Log.e("AddAppartmentActivity", "Response: " + response.toString());


                                List<Proprietaire> proprietaireList = response.body();

                                Log.e("AddAppartmentActivity", "proprietaireList: " + proprietaireList);


                                ArrayAdapter<Proprietaire> adapterv2 =
                                        new ArrayAdapter<>(
                                                AddAppartmentActivity.this,
                                                android.R.layout.simple_spinner_item,
                                                proprietaireList
                                        );

                                proprietaireSpinner.setAdapter(adapterv2);
                                proprietaireSpinner.setSelection(0);
                                proprietaireSpinner.setEnabled(false);
                            }

                        }
                        @Override
                        public void onFailure(Call<List<Proprietaire>> call, Throwable t) {
                            Toast.makeText(AddAppartmentActivity.this, getString(R.string.error_api_access), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

        }
        else{
            api.getProprietaires().enqueue(
                    new Callback<List<Proprietaire>>() {
                        @Override
                        public void onResponse(Call<List<Proprietaire>> call, Response<List<Proprietaire>> response) {
                            if (response.isSuccessful()) {
                                List<Proprietaire> proprietaires = response.body();

                                ArrayAdapter<Proprietaire> adapter =
                                        new ArrayAdapter<>(
                                                AddAppartmentActivity.this,
                                                android.R.layout.simple_spinner_item,
                                                proprietaires
                                        );

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                proprietaireSpinner.setAdapter(adapter);



                            }
                        }

                        @Override
                        public void onFailure(Call<List<Proprietaire>> call, Throwable t) {
                            Toast.makeText(AddAppartmentActivity.this, getString(R.string.error_api_access), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }

        Button btnAppartmentAdd = findViewById(R.id.btnAppartmentAdd);


        btnAppartmentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddAppartmentActivity.this, "Ajouter appartement", Toast.LENGTH_SHORT).show();
                addAppartment();
            }

        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public void addAppartment(){
        TextView txtAppartmentName = findViewById(R.id.txtAppartmentName);
        TextView txtAddress = findViewById(R.id.txtAddress);
        TextView txtAppartmentNumber = findViewById(R.id.txtAppartmentNumber);
        TextView txtKeyCode = findViewById(R.id.txtKeyCode);
        TextView txtDoorCode = findViewById(R.id.txtDoorCode);

        Log.d("AddAppartmentActivity", "On rentre bien dans l'ajout");


        String appartmentName = txtAppartmentName.getText().toString();
        String address = txtAddress.getText().toString();
        String appartmentNumber = txtAppartmentNumber.getText().toString();
        String keyCode = txtKeyCode.getText().isEmpty()? null : txtKeyCode.getText().toString();
        String doorCode = txtDoorCode.getText().isEmpty()? null : txtDoorCode.getText().toString();

        Spinner proprietaireSpinner = findViewById(R.id.proprietaireSpinner);
        Proprietaire proprietaire = (Proprietaire) proprietaireSpinner.getSelectedItem();

        if (appartmentName.isEmpty() || address.isEmpty() || appartmentNumber.isEmpty()) {
            txtAppartmentNumber.setError(getString(R.string.error_empty_fields));
            txtAddress.setError(getString(R.string.error_empty_fields));
            txtAppartmentName.setError(getString(R.string.error_empty_fields));
            return;
        }

        AppartementCreateDTO appart = new AppartementCreateDTO(
                proprietaire,
                appartmentName,
                address,
                appartmentNumber,
                keyCode,
                doorCode,
                0
                );

        APIInterface api = APIClient.createApi(getApplicationContext());

        api.createAppartement(appart).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.e("AddAppartmentActivity", "Response: " + response.toString());
                        Log.e("AddAppartmentActivity", "Response body: " + response.body());

                        if (response.isSuccessful()) {
                            Toast.makeText(AddAppartmentActivity.this, getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }


                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddAppartmentActivity.this, getString(R.string.error_api_access), Toast.LENGTH_SHORT).show();
                    }
                }
        );












    }


}