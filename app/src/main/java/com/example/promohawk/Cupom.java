package com.example.promohawk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Cupom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupom);
    }

    private String desconto;   // Exemplo: "50% OFF"
    private String descricao;  // Exemplo: "Em compras selecionadas"
    private String codigo;     // Exemplo: "ABCD123"
    private int imagem;        // Resource ID da imagem (R.drawable.amazon)

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