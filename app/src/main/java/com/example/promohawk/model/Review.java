package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    private int id;

    @SerializedName("usuario")
    private Usuario usuario;

    @SerializedName("comentario_produto")
    private String comentario;

    @SerializedName("avaliacao_produto")
    private float nota;

    @SerializedName("created_at")
    private String data;

    // Getters
    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
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
        this.usuario = usuario;
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
