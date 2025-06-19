package com.example.promohawk.model;

public class PerfilRequest {
    private String nome;
    private String email;
    private String telefone;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String imagemBase64;

    // Construtor
    public PerfilRequest(String nome, String email, String telefone,
                         String cep, String rua, String numero, String bairro,
                         String cidade, String estado,String imagemBase64) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.imagemBase64 = imagemBase64;
    }
    public String getImagemBase64() {
        return imagemBase64;
    }

    public PerfilRequest(String nome, String email, String telefone, String enderecoCompleto) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;

        // Aqui, você pode tentar dividir o endereço completo se quiser.
        this.cep = "";
        this.rua = enderecoCompleto; // ou separar se preferir
        this.numero = "";
        this.bairro = "";
        this.cidade = "";
        this.estado = "";
    }
}

