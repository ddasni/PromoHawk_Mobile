package com.example.promohawk;

public class LojaCard {
    private String nome;
    private int imagemRes;
    private Class<?> activityClass;

    public LojaCard(String nome, int imagemRes, Class<?> activityClass) {
        this.nome = nome;
        this.imagemRes = imagemRes;
        this.activityClass = activityClass;
    }

    public String getNome() {
        return nome;
    }

    public int getImagemRes() {
        return imagemRes;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }
}
