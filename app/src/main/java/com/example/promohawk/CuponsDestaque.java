package com.example.promohawk;

public class CuponsDestaque {

    private String desconto;   // Exemplo: "50% OFF"
    private String descricao;  // Exemplo: "Em compras selecionadas"
    private String codigo;     // Exemplo: "ABCD123"
    private int imagem;        // Resource ID da imagem (R.drawable.amazon)

    public CuponsDestaque(String desconto, String descricao, String codigo, int imagem) {
        this.desconto = desconto;
        this.descricao = descricao;
        this.codigo = codigo;
        this.imagem = imagem;
    }

    public String getDesconto() {
        return desconto;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getImagem() {
        return imagem;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
