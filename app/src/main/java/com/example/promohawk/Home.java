package com.example.promohawk;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        // Obtendo a referência para o ImageSlider
        ImageSlider imageSlider = findViewById(R.id.slider);

        // Criando a lista de imagens para o slider
        List<SlideModel> slideModels = new ArrayList<>();

        // Adicionando imagens locais
        slideModels.add(new SlideModel(R.drawable.img_1));

        // Adicionando imagens com URLs
        slideModels.add(new SlideModel("img_1.png"));
        slideModels.add(new SlideModel("https://picsum.photos/seed/picsum/200/300"));
        slideModels.add(new SlideModel("https://picsum.photos/200/300?grayscale"));
        slideModels.add(new SlideModel("https://picsum.photos/200/300/?blur"));

        // Definindo a lista de imagens no ImageSlider com a opção centerCrop
        imageSlider.setImageList(slideModels, true);  // 'true' significa centerCrop
    }
}