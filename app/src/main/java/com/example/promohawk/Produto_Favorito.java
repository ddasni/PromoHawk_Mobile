package com.example.promohawk;

public class Produto_Favorito {
    private int id; // id do produto
    private int favoritoId; // id do favorito na tabela favoritado (se existir)
    private String nome;
    private String preco;
    private String loja;
    private String imagem;
    private boolean favorito;

    // getters e setters
    public int getId() { return id; }
    public int getFavoritoId() { return favoritoId; }
    public void setFavoritoId(int favoritoId) { this.favoritoId = favoritoId; }
    public String getNome() { return nome; }
    public String getPreco() { return preco; }
    public String getLoja() { return loja; }
    public String getImagem() { return imagem; }
    public boolean isFavorito() { return favorito; }
    public void setFavorito(boolean favorito) { this.favorito = favorito; }
}


