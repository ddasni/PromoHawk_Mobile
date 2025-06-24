package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Preco implements Serializable {

    private int id;

    @SerializedName("produto_id")
    private int produtoId;

    private String preco;

    @SerializedName("forma_pagamento")
    private String formaPagamento;

    private int parcelas;

    @SerializedName("valor_parcela")
    private String valorParcela;

    @SerializedName("data_registro")
    private String dataRegistro;

    @SerializedName("data_alteração")
    private String dataAlteracao;

    private Loja loja;

    public int getId() {
        return id;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public String getPreco() {
        return preco;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public int getParcelas() {
        return parcelas;
    }

    public String getValorParcela() {
        return valorParcela;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public Loja getLoja() {
        return loja;
    }
}

