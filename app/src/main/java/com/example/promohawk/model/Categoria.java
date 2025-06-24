package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

public class Categoria {
    private int id;
    private String nome;

    @SerializedName("imagem")
    private String imagem;

    public Categoria() {}

    public Categoria(int id, String nome, String imagem) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
