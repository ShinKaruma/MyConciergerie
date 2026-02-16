package com.ShinKaruma.conciergerie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ShinKaruma.conciergerie.network.AuthApi;
import com.ShinKaruma.conciergerie.network.AuthApiClient;
import com.ShinKaruma.conciergerie.network.ConciergeRegister;

import com.ShinKaruma.conciergerie.network.ProprietaireRegister;
import com.ShinKaruma.conciergerie.pojo.Concierge;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView txtColor = view.findViewById(R.id.txtColor);
        txtColor.setVisibility(View.GONE);
        showColorPicker(txtColor);

        Switch userTypeSwitch = view.findViewById(R.id.userTypeSwitch);
        userTypeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> userTypeChange(userTypeSwitch, txtColor));

        Button btnInscription = view.findViewById(R.id.btnInscription);
        btnInscription.setOnClickListener(v -> register());



    }


    private void showColorPicker(TextView txtColor){
        txtColor.setOnClickListener(v ->{
                    new ColorPickerDialog.Builder(requireContext())
                            .setTitle(requireContext().getString(R.string.color_choice))
                            .setPositiveButton(
                                    requireContext().getString(R.string.ok),
                                    (ColorEnvelopeListener) (envelope, fromUser) -> {
                                        int color = envelope.getColor();
                                        txtColor.setTextColor(color);
                                        String hex = String.format("#%06X", (0xFFFFFF & color));
                                        txtColor.setText(hex);

                                    }
                            )
                            .setNegativeButton(
                                    requireContext().getString(R.string.cancel),
                                    (dialog, which) -> dialog.dismiss()
                            )
                            .show();


                }

        );
    }

    private void userTypeChange(Switch userTypeSwitch, TextView txtColor){
        if (userTypeSwitch.isChecked()) {
            txtColor.setVisibility(View.VISIBLE);
        } else {
            txtColor.setVisibility(View.GONE);
        }
    }

    private void register(){
        Switch userTypeSwitch = requireView().findViewById(R.id.userTypeSwitch);
        EditText txtEmail = requireView().findViewById(R.id.txtEmail);
        EditText txtPassword = requireView().findViewById(R.id.txtPassword);
        EditText txtName = requireView().findViewById(R.id.txtName);
        EditText txtSurname = requireView().findViewById(R.id.txtSurname);
        EditText txtPhone = requireView().findViewById(R.id.txtPhone);
        EditText txtCodeSynchro = requireView().findViewById(R.id.txtCodeSynchro);
        TextView txtColor = requireView().findViewById(R.id.txtColor);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String name = txtName.getText().toString();
        String surname = txtSurname.getText().toString();
        String phone = txtPhone.getText().toString();
        String codeSynchro = txtCodeSynchro.getText().toString();
        int colorInt = txtColor.getCurrentTextColor();
        String colorHex = String.format("#%06X", (0xFFFFFF & colorInt));

        Log.i("colorHex", colorHex);



        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() || phone.isEmpty() || codeSynchro.isEmpty()){
            TextView lblError = requireView().findViewById(R.id.lblErrorRegister);
            lblError.setText(getString(R.string.error_empty_fields));
            return;
        } else if (userTypeSwitch.isChecked() && colorHex.equals("#FFFFFF")) {
            TextView lblError = requireView().findViewById(R.id.lblErrorRegister);
            lblError.setText(getString(R.string.error_empty_fields));
            return;
        }

        if (!userTypeSwitch.isChecked()){
            ConciergeRegister user = new ConciergeRegister(email, password, name, surname, phone, codeSynchro);

            AuthApi api = AuthApiClient.getInstance();
            Call<ConciergeRegister> call = api.registerConcierge(user);

            call.enqueue(new Callback<ConciergeRegister>() {
                @Override
                public void onResponse(Call<ConciergeRegister> call, Response<ConciergeRegister> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), requireView().getContext().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                        Log.e("Register", response.body().toString());
                    }else{
                        TextView lblError = requireView().findViewById(R.id.lblErrorRegister);
                        lblError.setText(getString(R.string.error_account_creation));
                    }

                }

                @Override
                public void onFailure(Call<ConciergeRegister> call, Throwable t) {
                    TextView lblError = requireView().findViewById(R.id.lblErrorRegister);
                    lblError.setText(getString(R.string.error_api_access));
                }
            });




        }else {
            ProprietaireRegister user = new ProprietaireRegister(email, password, name, surname, phone, codeSynchro, colorHex);

            AuthApi api = AuthApiClient.getInstance();
            Call<ProprietaireRegister> call = api.registerProprietaire(user);

            call.enqueue(new Callback<ProprietaireRegister>() {
                @Override
                public void onResponse(Call<ProprietaireRegister> call, Response<ProprietaireRegister> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), requireView().getContext().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                        Log.e("Register", response.body().toString());
                    }else{
                        TextView lblError = requireView().findViewById(R.id.lblErrorRegister);
                        lblError.setText(getString(R.string.error_account_creation));
                    }

                }

                @Override
                public void onFailure(Call<ProprietaireRegister> call, Throwable t) {
                    TextView lblError = requireView().findViewById(R.id.lblErrorRegister);
                    lblError.setText(getString(R.string.error_api_access));
                }
            });

        }

    }

}