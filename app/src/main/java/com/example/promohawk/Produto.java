package com.example.promohawk;

public class Produto {
    private String nome;
    private int imagem; // ID do recurso da imagem (ex: R.drawable.exemplo)

    public Produto(String nome, int imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public int getImagem() {
        return imagem;
    }
}
