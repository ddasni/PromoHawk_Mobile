package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Produto implements Serializable {

    private int id;

    private String nome;

    private String descricao;

    @SerializedName("media_nota")
    private float avaliacao;

    @SerializedName("link")
    private String urlLoja;

    private List<String> imagens;

    @SerializedName("categoria")
    private Categoria categoria; // Objeto categoria

    @SerializedName("precos")
    private List<Preco> precos; // Lista de objetos de preço

    // Para gráfico de histórico (preencher depois com base na lógica, se necessário)
    private List<Float> historicoPrecos;

    // ==== Getters ====

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

    public String getImagemUrl() {
        return (imagens != null && !imagens.isEmpty()) ? imagens.get(0) : null;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<Preco> getPrecos() {
        return precos;
    }

    public List<Float> getHistoricoPrecos() {
        return historicoPrecos;
    }

    // Opcional: setter para histórico (se precisar preencher manualmente)
    public void setHistoricoPrecos(List<Float> historicoPrecos) {
        this.historicoPrecos = historicoPrecos;
    }
}
