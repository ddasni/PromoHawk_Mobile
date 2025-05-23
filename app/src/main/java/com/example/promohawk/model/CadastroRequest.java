package com.example.promohawk.model;

public class CadastroRequest {
    private String username;
    private String nome;
    private String telefone;
    private String email;
    private String password;

    public CadastroRequest(String nome, String email, String password, String telefone, String username) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.telefone = telefone;
        this.username = username;
    }

    // getters e setters
}
