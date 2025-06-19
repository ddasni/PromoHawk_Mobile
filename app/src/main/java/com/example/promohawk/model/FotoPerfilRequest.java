package com.example.promohawk.model;

public class FotoPerfilRequest {
    private String email;
    private String fotoUrl; // pode ser url ou base64, dependendo da sua API

    public FotoPerfilRequest(String email, String fotoUrl) {
        this.email = email;
        this.fotoUrl = fotoUrl;
    }

    // getters e setters (se precisar)
}
