package com.example.promohawk;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtendo a referência para o ImageSlider
        ImageSlider imageSlider = findViewById(R.id.slider);

        // Criando a lista de imagens para o slider
        List<SlideModel> slideModels = new ArrayList<>();

        // Adicionando imagens locais
        slideModels.add(new SlideModel(R.drawable.img_1));

        // Adicionando imagens com URLs
        slideModels.add(new SlideModel("https://picsum.photos/id/237/200/300"));
        slideModels.add(new SlideModel("https://picsum.photos/seed/picsum/200/300"));
        slideModels.add(new SlideModel("https://picsum.photos/200/300?grayscale"));
        slideModels.add(new SlideModel("https://picsum.photos/200/300/?blur"));

        // Definindo a lista de imagens no ImageSlider com a opção centerCrop
        imageSlider.setImageList(slideModels, true);  // 'true' significa centerCrop

        // Iniciando a atividade de Login após 3 segundos
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();  // Finaliza a MainActivity
            }
        }, 3000);
    }
}

