package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    private int id;

    @SerializedName("usuario")
    private String nomeUsuario;

    @SerializedName("comentario")
    private String comentario;

    @SerializedName("nota")
    private float nota;

    @SerializedName("data")
    private String data;

    // Getters
    public int getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public float getNota() {
        return nota;
    }

    public String getData() {
        return data;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public void setData(String data) {
        this.data = data;
    }
}
