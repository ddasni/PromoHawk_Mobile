package com.example.promohawk;

import java.io.Serializable;
import java.util.List;

public class Produto implements Serializable {
    private int id; // ID do produto adicionado
    private String nome;
    private String preco;
    private String melhorPreco;
    private float avaliacao;
    private int qtdAvaliacoes;
    private String urlLoja;
    private List<String> imagens; // URLs das imagens
    private List<Float> historicoPrecos; // Preços do gráfico

    // Getter para o ID
    public int getId() {
        return id;
    }

    // Getters necessários para o adapter
    public String getNome() {
        return nome;
    }

    public String getPreco() {
        return preco;
    }

    public String getImagemUrl() {
        return imagens != null && !imagens.isEmpty() ? imagens.get(0) : null;
    }

    // Getters adicionais, se quiser
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

    public List<Float> getHistoricoPrecos() {
        return historicoPrecos;
    }
}
