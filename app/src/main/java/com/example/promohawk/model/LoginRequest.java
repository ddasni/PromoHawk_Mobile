package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    private String email;

    @SerializedName("password") // aqui trocamos o nome que será enviado no JSON
    private String senha;

    public LoginRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters (se precisar)
}
