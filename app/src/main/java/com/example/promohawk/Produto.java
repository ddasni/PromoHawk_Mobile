package com.example.promohawk;

import java.io.Serializable;
import java.util.List;

public class Produto implements Serializable {

    private int id;
    private String nome;
    private String preco;
    private String melhorPreco;
    private float avaliacao;
    private int qtdAvaliacoes;
    private String urlLoja;
    private List<String> imagens;
    private List<Float> historicoPrecos;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPreco() {
        return preco;
    }

    public String getMelhorPreco() {
        return melhorPreco;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public int getQtdAvaliacoes() {
        return qtdAvaliacoes;
    }

    public String getUrlLoja() {
        return urlLoja;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public String getImagemUrl() {
        return (imagens != null && !imagens.isEmpty()) ? imagens.get(0) : null;
    }

    public List<Float> getHistoricoPrecos() {
        return historicoPrecos;
    }
}
