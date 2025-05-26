package com.example.promohawk;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
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

        slideModels.add(new SlideModel(R.drawable.img_1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://picsum.photos/seed/picsum/200/300", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://picsum.photos/200/300?grayscale", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://picsum.photos/200/300/?blur", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);

// Iniciar troca automática de imagens a cada 2 segundos (por exemplo)
        imageSlider.startSliding(2000);


        // ====== Configurando cliques ======
        verMais = findViewById(R.id.verMaisCategorias);
        verMais.setOnClickListener(v -> {
            Toast.makeText(Home.this, "Ver mais clicado!", Toast.LENGTH_SHORT).show();
        });

        imgEletronicos = findViewById(R.id.imgEletronicos);
        imgVideoGames = findViewById(R.id.imgVideoGames);
        imgSmartphones = findViewById(R.id.imgSmartphones);
        imgCalcados = findViewById(R.id.imgCalcados);
        imgLivros = findViewById(R.id.imgLivros);

        imgEletronicos.setOnClickListener(v -> abrirCategoria("Eletrônicos"));
        imgVideoGames.setOnClickListener(v -> abrirCategoria("Video Games"));
        imgSmartphones.setOnClickListener(v -> abrirCategoria("Smartphones"));
        imgCalcados.setOnClickListener(v -> abrirCategoria("Calçados"));
        imgLivros.setOnClickListener(v -> abrirCategoria("Livros"));

        // ====== Riscando preço antigo ======
        TextView precoAntigo1 = findViewById(R.id.precoAntigoXboxS); // certifique-se que esse ID está no XML
        TextView precoAntigo2 = findViewById(R.id.precoAntigoXboxX);
        TextView precoAntigo3 = findViewById(R.id.precoAntigoPS5);
        TextView precoAntigo4 = findViewById(R.id.precoAntigoPS4);

        precoAntigo1.setPaintFlags(precoAntigo1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        precoAntigo2.setPaintFlags(precoAntigo2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        precoAntigo3.setPaintFlags(precoAntigo3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        precoAntigo4.setPaintFlags(precoAntigo4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void abrirCategoria(String nomeCategoria) {
        Toast.makeText(this, "Categoria: " + nomeCategoria, Toast.LENGTH_SHORT).show();
    }
}
