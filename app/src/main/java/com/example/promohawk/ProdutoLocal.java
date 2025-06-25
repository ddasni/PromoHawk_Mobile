package com.example.promohawk;

public class ProdutoLocal {
    public int id;
    public String nome;
    public String preco;
    public String nota;
    public boolean favorito;
    public int imagemResId;  // campo da imagem

    public ProdutoLocal(int id, String nome, String preco, String nota, boolean favorito, int imagemResId) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.nota = nota;
        this.favorito = favorito;
        this.imagemResId = imagemResId;
    }
}
