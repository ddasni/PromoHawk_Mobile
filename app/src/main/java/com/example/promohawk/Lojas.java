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
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lojas);

        recyclerView = findViewById(R.id.recyclerLojas);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new LojaAdapter(context, getListaLojas()));
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.animation_fall_down));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        configurarBottomNavigation();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_lojas);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(context, Home.class));
            } else if (id == R.id.nav_produtos) {
                startActivity(new Intent(context, ProdutosActivity.class));
            } else if (id == R.id.nav_cupons) {
                startActivity(new Intent(context, CuponsActivity.class));
            } else if (id == R.id.nav_config) {
                startActivity(new Intent(context, Config.class));
            } else if (id == R.id.nav_lojas) {
                return true;
            }
            overridePendingTransition(0, 0);
            finish();
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
