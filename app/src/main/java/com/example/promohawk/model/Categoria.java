package com.example.promohawk.model;

import java.io.Serializable;

public class Categoria implements Serializable {
    private int id;
    private String nome;
    private String imagem;

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