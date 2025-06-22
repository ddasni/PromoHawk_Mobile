package com.example.promohawk.model;

import com.google.gson.annotations.SerializedName;

public class Cupom {

    @SerializedName("codigo")
    private String codigo;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("desconto")
    private String desconto;

    @SerializedName("validade")
    private String data;

    // Getters e Setters
    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
