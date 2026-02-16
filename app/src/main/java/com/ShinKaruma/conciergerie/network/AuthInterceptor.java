package com.ShinKaruma.conciergerie.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final SessionManager sessionManager;
    private final TokenRefreshService refreshService;

    public AuthInterceptor(
            SessionManager sessionManager,
            TokenRefreshService refreshService
    ) {
        this.sessionManager = sessionManager;
        this.refreshService = refreshService;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        String accessToken = sessionManager.getAccessToken();

        if (accessToken != null) {
            request = request.newBuilder()
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
        }

        okhttp3.Response response = chain.proceed(request);

        if (response.code() == 401) {
            synchronized (this) {

                String refreshToken = sessionManager.getRefreshToken();
                if (refreshToken == null) return response;

                TokenPair newTokens = refreshService.refresh(refreshToken);

                if (newTokens != null) {
                    sessionManager.saveAccessToken(newTokens.getAccessToken());
                    sessionManager.saveRefreshToken(newTokens.getRefreshToken());




                    Request retry = request.newBuilder()
                            .header("Authorization", "Bearer " + newTokens.getAccessToken())
                            .build();

                    response.close();
                    return chain.proceed(retry);
                }
            }
        }

        return response;
    }
}

