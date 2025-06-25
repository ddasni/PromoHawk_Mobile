package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Categoria implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("imagem")
    private String imagem;

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getImagem() {
        return imagem;
    }
}
