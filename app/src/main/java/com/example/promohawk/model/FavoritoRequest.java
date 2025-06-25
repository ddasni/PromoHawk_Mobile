package com.example.promohawk.model;

public class FavoritoRequest {
    private int produtoId;

    public FavoritoRequest(int produtoId) {
        this.produtoId = produtoId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }
}
