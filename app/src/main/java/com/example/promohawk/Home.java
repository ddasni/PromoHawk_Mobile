package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private ImageView imgEletronicos, imgVideoGames, imgSmartphones, imgCalcados, imgLivros;
    private TextView verMais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // ====== Configurando o ImageSlider ======
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.img_1));
        slideModels.add(new SlideModel("img_1.png"));
        slideModels.add(new SlideModel("https://picsum.photos/seed/picsum/200/300"));
        slideModels.add(new SlideModel("https://picsum.photos/200/300?grayscale"));
        slideModels.add(new SlideModel("https://picsum.photos/200/300/?blur"));

        imageSlider.setImageList(slideModels, true);

        // ====== Configurando cliques ======

        verMais = findViewById(R.id.verMaisCategorias);
        verMais.setOnClickListener(v -> {
            Toast.makeText(Home.this, "Ver mais clicado!", Toast.LENGTH_SHORT).show();
            // Exemplo de navegação: startActivity(new Intent(Home.this, CategoriasActivity.class));
        });

        // Referências para cada categoria
        imgEletronicos = findViewById(R.id.imgEletronicos);
        imgVideoGames = findViewById(R.id.imgVideoGames);
        imgSmartphones = findViewById(R.id.imgSmartphones);
        imgCalcados = findViewById(R.id.imgCalcados);
        imgLivros = findViewById(R.id.imgLivros);

        // Configurar os listeners
        imgEletronicos.setOnClickListener(v -> abrirCategoria("Eletrônicos"));
        imgVideoGames.setOnClickListener(v -> abrirCategoria("Video Games"));
        imgSmartphones.setOnClickListener(v -> abrirCategoria("Smartphones"));
        imgCalcados.setOnClickListener(v -> abrirCategoria("Calçados"));
        imgLivros.setOnClickListener(v -> abrirCategoria("Livros"));
    }

    private void abrirCategoria(String nomeCategoria) {
        Toast.makeText(this, "Categoria: " + nomeCategoria, Toast.LENGTH_SHORT).show();
        // Exemplo de navegação:
        // Intent intent = new Intent(Home.this, CategoriaDetalhesActivity.class);
        // intent.putExtra("categoria", nomeCategoria);
        // startActivity(intent);
    }
}
