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

        initializeViews();
        setupRecyclerView();
        configurarBottomNavigation();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerLojas);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void setupRecyclerView() {
        // Configuração do LayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Configuração do Adapter
        LojasAdapter adapter = new LojasAdapter(getStoreList(), this);
        recyclerView.setAdapter(adapter);

        // Animação e espaçamento
        setupRecyclerViewAnimations();
        addItemDecoration();
    }

    private void setupRecyclerViewAnimations() {
        recyclerView.setLayoutAnimation(
                AnimationUtils.loadLayoutAnimation(this, R.anim.animation_fall_down)
        );
    }

    private void addItemDecoration() {
        int spanCount = 2; // Número de colunas
        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing); // 16dp
        boolean includeEdge = true;

        recyclerView.addItemDecoration(
                new GridSpacingItemDecoration(spanCount, spacing, includeEdge)
        );
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_lojas);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Context context = this;

            if (id == R.id.nav_home) {
                startActivity(new Intent(context, Home.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_produtos) {
                startActivity(new Intent(context, ProdutosActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_cupons) {
                startActivity(new Intent(context, CuponsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_config) {
                startActivity(new Intent(context, Config.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_lojas) {
                return true; // Já está na tela de lojas
            }

            return false;
        });
    }

    private void applyTransition() {
        overridePendingTransition(0, 0);
        finish();
    }

    private List<LojaCard> getStoreList() {
        return Arrays.asList(
                new LojaCard(getString(R.string.amazon), R.drawable.logo_seta_amazon, Amazon.class),
                new LojaCard(getString(R.string.centauro), R.drawable.logo_centauro, Centauro.class),
                new LojaCard(getString(R.string.kabum), R.drawable.logo_kabum, Kabum.class),
                new LojaCard(getString(R.string.pichau), R.drawable.logo_pichau, Pichau.class),
                new LojaCard(getString(R.string.magazine), R.drawable.logo_magazine, Magazine.class),
                new LojaCard(getString(R.string.mercado_livre), R.drawable.logo_mercadolivre, Mercado_Livre.class),

                // Novas lojas adicionadas
                new LojaCard(getString(R.string.kz), R.drawable.logo_kz, Kz.class),
                new LojaCard(getString(R.string.nike), R.drawable.logo_nike, Nike.class),
                new LojaCard(getString(R.string.dolce_gusto), R.drawable.logo_dolce_gusto, DolceGusto.class),
                new LojaCard(getString(R.string.gopro), R.drawable.logo_gopro, GoPro.class),
                new LojaCard(getString(R.string.apple), R.drawable.logo_apple, AppleStore.class),
                new LojaCard(getString(R.string.submarino), R.drawable.logo_submarino, Submarino.class),
                new LojaCard("samsung", R.drawable.logo_samsung, samsung.class),
                new LojaCard("Sony", R.drawable.logo_sony, Sony.class),
                new LojaCard(getString(R.string.lg), R.drawable.logo_lg, LG.class));

            }

    }

