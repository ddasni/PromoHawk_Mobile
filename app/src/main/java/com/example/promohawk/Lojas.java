package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class Lojas extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lojas);

        // Inicialização dos componentes
        initViews();
        setupRecyclerView();
        setupBottomNavigation();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerLojas);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void setupRecyclerView() {
        // Configuração do LayoutManager com 2 colunas
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Configuração do Adapter
        LojasAdapter adapter = new LojasAdapter(getListaLojas(), this);
        recyclerView.setAdapter(adapter);

        // Animação para os itens
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(
                this, R.anim.animation_fall_down));

        // Adiciona espaçamento entre os itens (opcional)
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 16, true));
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_lojas);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_lojas) {
                return true; // Já estamos nesta tela
            }

            // Navegação para outras telas
            Class<?> destination = null;
            if (id == R.id.nav_home) {
                destination = Home.class;
            } else if (id == R.id.nav_produtos) {
                destination = ProdutosActivity.class;
            } else if (id == R.id.nav_cupons) {
                destination = CuponsActivity.class;
            } else if (id == R.id.nav_config) {
                destination = Config.class;
            }

            if (destination != null) {
                startActivity(new Intent(this, destination));
                overridePendingTransition(0, 0);
                finish();
            }

            return true;
        });
    }

    private List<LojaCard> getListaLojas() {
        return Arrays.asList(
                new LojaCard("Amazon", R.drawable.amazon, Amazon.class),
                new LojaCard("Centauro", R.drawable.centauru, Centauro.class),
                new LojaCard("Kabum", R.drawable.kabum, Kabum.class),
                new LojaCard("Pichau", R.drawable.pichau3, Pichau.class),
                new LojaCard("Magazine", R.drawable.magazine2, Magazine.class),
                new LojaCard("Mercado Livre", R.drawable.mercado2, Mercado_Livre.class)
        );
    }
}