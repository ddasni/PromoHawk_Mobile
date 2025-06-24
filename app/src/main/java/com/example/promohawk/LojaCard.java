package com.example.promohawk;

public class LojaCard {


    private String nome;
    private int imagemResId;
    private Class<?> activityDestino;

    public LojaCard(String nome, int imagemResId, Class<?> activityDestino) {
        this.nome = nome;
        this.imagemResId = imagemResId;
        this.activityDestino = activityDestino;
    }

    // Getters
    public String getNome() { return nome; }
    public int getImagemResId() { return imagemResId; }
    public Class<?> getActivityDestino() { return activityDestino; }
}