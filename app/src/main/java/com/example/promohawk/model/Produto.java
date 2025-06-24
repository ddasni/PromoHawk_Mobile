package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Produto implements Serializable {

    private int id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("media_nota")
    private float avaliacao;

    @SerializedName("link")
    private String urlLoja;

    private List<String> imagens;

    private Categoria categoria;

    private List<Preco> precos;

    private List<Float> historicoPrecos;

    @SerializedName("reviews")
    private List<Review> reviews;

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public String getUrlLoja() {
        return urlLoja;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public String getImagem() {
        return (imagens != null && !imagens.isEmpty()) ? imagens.get(0) : null;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<Preco> getPrecos() {
        return precos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getPreco() {
        return (precos != null && !precos.isEmpty()) ? precos.get(0).getPreco() : null;
    }

    public String getMelhorPreco() {
        return getPreco(); // ou lógica para menor preço
    }


    public List<Float> getHistoricoPrecos() {
        return historicoPrecos;
    }

    public void setHistoricoPrecos(List<Float> historicoPrecos) {
        this.historicoPrecos = historicoPrecos;
    }
}