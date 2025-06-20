package com.example.promohawk.model;

public class Cupom {
    private String imagemUrl;
    private String desconto;
    private String descricao;
    private String codigo;
    private String data; // nova propriedade

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public String getDesconto() { return desconto; }
    public void setDesconto(String desconto) { this.desconto = desconto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
