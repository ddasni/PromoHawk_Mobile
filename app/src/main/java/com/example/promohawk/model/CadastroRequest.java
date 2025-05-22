package com.example.promohawk.model;

public class CadastroRequest {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String data;
    private  String confirmarSenha;

    public CadastroRequest(String nome, String email, String senha, String telefone, String confirmarSenha, String data) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.confirmarSenha = confirmarSenha;
        this.data = data;
    }

    // Getters e setters (opcional, dependendo da API)
}
