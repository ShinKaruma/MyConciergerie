package com.ShinKaruma.conciergerie;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ShinKaruma.conciergerie.network.APIClient;
import com.ShinKaruma.conciergerie.network.APIInterface;
import com.ShinKaruma.conciergerie.network.AuthApi;
import com.ShinKaruma.conciergerie.network.AuthApiClient;
import com.ShinKaruma.conciergerie.network.LoginRequest;
import com.ShinKaruma.conciergerie.network.SessionManager;
import com.ShinKaruma.conciergerie.network.TokenPair;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> login());
    }

    private void login(){
        TextView txtEmail = requireView().findViewById(R.id.pEmail);
        TextView txtPassword = requireView().findViewById(R.id.pPassword);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            TextView lblError = requireView().findViewById(R.id.lblError);
            lblError.setText(getString(R.string.error_empty_fields));
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);

        AuthApi api = AuthApiClient.getInstance();

        Call<TokenPair> call = api.login(loginRequest);



            call.enqueue(new Callback<TokenPair>(){


                @Override
                public void onResponse(Call<TokenPair> call, Response<TokenPair> response) {
                    if(response.isSuccessful()){
                        TokenPair body = response.body();
                        SessionManager sessionManager = SessionManager.getInstance(requireContext());

                        if (body != null) {
                            sessionManager.saveAccessToken(body.getAccessToken());
                            sessionManager.saveRefreshToken(body.getRefreshToken());
                            Toast.makeText(requireContext(), requireView().getContext().getString(R.string.auth_success), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();

                        }



                    }
                }

                @Override
                public void onFailure(Call<TokenPair> call, Throwable t) {
                    TextView lblError = requireView().findViewById(R.id.lblError);
                    lblError.setText(getString(R.string.error_api_access));
                }
            });
    }
}