package com.ShinKaruma.conciergerie.network;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JwtUtils {

    public static boolean isExpired(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            if (parts.length != 3) return true;

            String payloadJson = new String(
                    Base64.decode(parts[1], Base64.URL_SAFE),
                    StandardCharsets.UTF_8
            );

            JSONObject payload = new JSONObject(payloadJson);
            long exp = payload.getLong("exp");

            long now = System.currentTimeMillis() / 1000;
            return exp < now;

        } catch (Exception e) {
            return true;
        }
    }
}

