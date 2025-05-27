package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Config extends AppCompatActivity {

    private ImageView imgPerfil;
    private ImageView btnVoltar;  // mudar para ImageView, pois no XML é ImageView
    private LinearLayout btnPerfil, btnFavoritos, btnConta, btnHistorico, btnSuporte;  // mudar para LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Botão voltar (ImageView)
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Config.this, Home.class);
            startActivity(intent);
            finish();
        });

        // Imagem do perfil
        imgPerfil = findViewById(R.id.imgPerfil);
        imgPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(Config.this, Perfil.class);
            startActivity(intent);
        });

        // Botões (LinearLayout clicáveis)
        btnPerfil = findViewById(R.id.btnPerfil);
        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnConta = findViewById(R.id.btnConta);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnSuporte = findViewById(R.id.btnSuporte);

        btnPerfil.setOnClickListener(v -> startActivity(new Intent(this, Perfil.class)));
        btnFavoritos.setOnClickListener(v -> startActivity(new Intent(this, Favoritos.class)));
        btnConta.setOnClickListener(v -> startActivity(new Intent(this, Config_interna.class)));
        btnHistorico.setOnClickListener(v -> startActivity(new Intent(this, Historico.class)));
        btnSuporte.setOnClickListener(v -> startActivity(new Intent(this, Suporte.class)));
    }
}
