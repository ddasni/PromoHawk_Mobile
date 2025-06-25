package com.example.promohawk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Amazon extends AppCompatActivity {

    private ImageView btnFavoritar11, btnFavoritar12, btnFavoritar3, btnFavoritar4;
    private boolean isFavorito11 = false, isFavorito12 = false, isFavorito3 = false, isFavorito4 = false;
    private SharedPreferences prefs;

    private CardView cardProduto11, cardProduto12, cardProduto3, cardProduto4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja_amazon); // seu XML da loja

        prefs = getSharedPreferences("favoritos_prefs", MODE_PRIVATE);

        // Inicializa os cards
        cardProduto11 = findViewById(R.id.cardProduto11);
        cardProduto12 = findViewById(R.id.cardProduto12);
        cardProduto3 = findViewById(R.id.cardProduto3);
        cardProduto4 = findViewById(R.id.cardProduto4);

        // Inicializa os botões favoritos
        btnFavoritar11 = findViewById(R.id.btnFavoritar11);
        btnFavoritar12 = findViewById(R.id.btnFavoritar12);
        btnFavoritar3 = findViewById(R.id.btnFavoritar3);
        btnFavoritar4 = findViewById(R.id.btnFavoritar4);

        // Recupera status favoritos do SharedPreferences
        isFavorito11 = prefs.getBoolean("favorito_11", false);
        isFavorito12 = prefs.getBoolean("favorito_12", false);
        isFavorito3 = prefs.getBoolean("favorito_3", false);
        isFavorito4 = prefs.getBoolean("favorito_4", false);

        atualizarIcone(btnFavoritar11, isFavorito11);
        atualizarIcone(btnFavoritar12, isFavorito12);
        atualizarIcone(btnFavoritar3, isFavorito3);
        atualizarIcone(btnFavoritar4, isFavorito4);

        // URLs dos produtos
        String urlProduto11 = "https://www.amazon.com.br/PlayStation%C2%AE5-Slim-Edi%C3%A7%C3%A3o-Digital-Jogos/dp/B0CYJBWGH5/ref=asc_df_B0CYJBWGH5?mcid=24deaabda68e3b9aa3150dfa1ab41c61&tag=googleshopp00-20&linkCode=df0&hvadid=709884703642&hvpos=&hvnetw=g&hvrand=3682782172519802435&hvpone=&hvptwo=&hvqmt=&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9197305&hvtargid=pla-2300389685916&psc=1&language=pt_BR&gad_source=1";

        String urlProduto12 = "https://www.amazon.com.br/Samsung-Smartwatch-Galaxy-Classic-Grafite/dp/B0CCQP9JB7/ref=asc_df_B0CCQP9JB7?mcid=005ee85dc1a93678940be82921895baf&tag=googleshopp00-20&linkCode=df0&hvadid=709964503139&hvpos=&hvnetw=g&hvrand=12648078138559831829&hvpone=&hvptwo=&hvqmt=&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9197305&hvtargid=pla-2202862613217&language=pt_BR&gad_source=1&th=1";

        // Para os produtos 3 e 4 coloquei links fictícios, substitua pelos reais
        String urlProduto3 = "https://www.amazon.com.br/ExemploProduto3";
        String urlProduto4 = "https://www.amazon.com.br/ExemploProduto4";

        // Clique nos cards para abrir o link
        cardProduto11.setOnClickListener(v -> abrirLink(urlProduto11));
        cardProduto12.setOnClickListener(v -> abrirLink(urlProduto12));
        cardProduto3.setOnClickListener(v -> abrirLink(urlProduto3));
        cardProduto4.setOnClickListener(v -> abrirLink(urlProduto4));

        // Clique para favoritar/desfavoritar produtos
        btnFavoritar11.setOnClickListener(v -> {
            isFavorito11 = !isFavorito11;
            prefs.edit().putBoolean("favorito_11", isFavorito11).apply();
            atualizarIcone(btnFavoritar11, isFavorito11);
        });

        btnFavoritar12.setOnClickListener(v -> {
            isFavorito12 = !isFavorito12;
            prefs.edit().putBoolean("favorito_12", isFavorito12).apply();
            atualizarIcone(btnFavoritar12, isFavorito12);
        });

        btnFavoritar3.setOnClickListener(v -> {
            isFavorito3 = !isFavorito3;
            prefs.edit().putBoolean("favorito_3", isFavorito3).apply();
            atualizarIcone(btnFavoritar3, isFavorito3);
        });

        btnFavoritar4.setOnClickListener(v -> {
            isFavorito4 = !isFavorito4;
            prefs.edit().putBoolean("favorito_4", isFavorito4).apply();
            atualizarIcone(btnFavoritar4, isFavorito4);
        });
    }

    private void atualizarIcone(ImageView btnFavoritar, boolean isFavorito) {
        if (isFavorito) {
            btnFavoritar.setImageResource(R.drawable.ic_favorite_preenchido_preto);
        } else {
            btnFavoritar.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void abrirLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
