package com.example.promohawk;

public class Categoria {
    private int id;
    private String nome;
    private String imagemUrl;

    public Categoria() {}

    public Categoria(int id, String nome, String imagemUrl) {
        this.id = id;
        this.nome = nome;
        this.imagemUrl = imagemUrl;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
