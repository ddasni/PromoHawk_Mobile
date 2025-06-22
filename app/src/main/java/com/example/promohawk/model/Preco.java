package com.example.promohawk.model;

public class Preco {
    private int id;
    private int produto_id;
    private String preco;
    private String forma_pagamento;
    private int parcelas;
    private String valor_parcela;
    private String data_registro;
    private String data_alteracao;
    private Loja loja;

    public Preco() {}

    public int getId() {
        return id;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public String getPreco() {
        return preco;
    }

    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public int getParcelas() {
        return parcelas;
    }

    public String getValor_parcela() {
        return valor_parcela;
    }

    public String getData_registro() {
        return data_registro;
    }

    public String getData_alteracao() {
        return data_alteracao;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setForma_pagamento(String forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public void setValor_parcela(String valor_parcela) {
        this.valor_parcela = valor_parcela;
    }

    public void setData_registro(String data_registro) {
        this.data_registro = data_registro;
    }

    public void setData_alteracao(String data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }
}
