package com.example.promohawk;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Kz extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja_kz);

        ImageView btnFav1 = findViewById(R.id.btnFavoritar1);
        String idProdutoKZ1 = "kz_zs10";

        btnFav1.setImageResource(
                FavoritosManager.isFavorito(this, idProdutoKZ1) ?
                        R.drawable.ic_favorite_preenchido_preto : R.drawable.ic_favorite_border
        );

        btnFav1.setOnClickListener(v -> {
            FavoritosManager.toggleFavorito(this, idProdutoKZ1);
            btnFav1.setImageResource(
                    FavoritosManager.isFavorito(this, idProdutoKZ1) ?
                            R.drawable.ic_favorite_preenchido_preto : R.drawable.ic_favorite_border
            );
        });
// troque para o nome certo do seu layout
    }
}
