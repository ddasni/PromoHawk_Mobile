package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Config extends AppCompatActivity {

    private ImageView imgPerfil;
    private ImageButton btnVoltar;
    private MaterialButton btnPerfil, btnFavoritos, btnConta, btnHistorico,
            btnPagamentos, btnEnderecos, btnSeguranca, btnSuporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Botão voltar (ImageButton)
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

        // Botões MaterialButton
        btnPerfil = findViewById(R.id.btnPerfil);
        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnConta = findViewById(R.id.btnConta);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnPagamentos = findViewById(R.id.btnPagamentos);
        btnEnderecos = findViewById(R.id.btnEnderecos);
        btnSeguranca = findViewById(R.id.btnSeguranca);
        btnSuporte = findViewById(R.id.btnSuporte);

        btnPerfil.setOnClickListener(v -> startActivity(new Intent(this, Perfil.class)));
        btnFavoritos.setOnClickListener(v -> startActivity(new Intent(this, Favoritos.class))); //Favoritos
        btnConta.setOnClickListener(v -> startActivity(new Intent(this, Usuario.class))); //usuario
        btnHistorico.setOnClickListener(v -> startActivity(new Intent(this, Historico.class))); //Historico
        btnPagamentos.setOnClickListener(v -> startActivity(new Intent(this, Pagamentos.class))); //Pagamentos
        btnEnderecos.setOnClickListener(v -> startActivity(new Intent(this, produtos.class)));// Endereços
        btnSeguranca.setOnClickListener(v -> startActivity(new Intent(this, Seguranca.class)));
        btnSuporte.setOnClickListener(v -> startActivity(new Intent(this, Suporte.class)));
    }
}
