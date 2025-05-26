package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("access_token") // isso mapeia o JSON "access_token"
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
