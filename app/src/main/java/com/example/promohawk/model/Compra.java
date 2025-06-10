package com.example.promohawk.model;

public class Compra {
    private String data;
    private String nomeProduto;
    private String preco;
    private String loja;
    private String imagemUrl; // se vier uma imagem da API

    // Getters e setters
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public String getPreco() { return preco; }
    public void setPreco(String preco) { this.preco = preco; }

    public String getLoja() { return loja; }
    public void setLoja(String loja) { this.loja = loja; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
}
