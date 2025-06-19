package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("email")
    private String email;

    @SerializedName("nome")
    private String nome;

    @SerializedName("foto_url")
    private String fotoUrl;

    public String getAccessToken() {
        return accessToken;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }
}
