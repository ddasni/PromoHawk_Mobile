package com.example.promohawk.model;

public class FotoPerfilBase64Request {
    private String email;
    private String fotoBase64;

    public FotoPerfilBase64Request(String email, String fotoBase64) {
        this.email = email;
        this.fotoBase64 = fotoBase64;
    }
}
