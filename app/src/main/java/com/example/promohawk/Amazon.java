package com.example.promohawk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Amazon extends AppCompatActivity {

    private ImageView btnFavoritar11, btnFavoritar12, btnFavoritar13, btnFavoritar14;
    private boolean isFavorito11 = false, isFavorito12 = false, isFavorito13 = false, isFavorito14 = false;
    private SharedPreferences prefs;

    private CardView cardProduto11, cardProduto12, cardProduto13, cardProduto14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja_amazon); // seu XML da loja

        prefs = getSharedPreferences("favoritos_prefs", MODE_PRIVATE);

        // Inicializa os cards
        cardProduto11 = findViewById(R.id.cardProduto11);
        cardProduto12 = findViewById(R.id.cardProduto12);
        cardProduto13 = findViewById(R.id.cardProduto13);
        cardProduto14 = findViewById(R.id.cardProduto14);

        // Inicializa os botões favoritos
        btnFavoritar11 = findViewById(R.id.btnFavoritar11);
        btnFavoritar12 = findViewById(R.id.btnFavoritar12);
        btnFavoritar13 = findViewById(R.id.btnFavoritar13);
        btnFavoritar14 = findViewById(R.id.btnFavoritar14);

        // Recupera status favoritos do SharedPreferences
        isFavorito11 = prefs.getBoolean("favorito_11", false);
        isFavorito12 = prefs.getBoolean("favorito_12", false);
        isFavorito13 = prefs.getBoolean("favorito_13", false);
        isFavorito14 = prefs.getBoolean("favorito_14", false);

        atualizarIcone(btnFavoritar11, isFavorito11);
        atualizarIcone(btnFavoritar12, isFavorito12);
        atualizarIcone(btnFavoritar13, isFavorito13);
        atualizarIcone(btnFavoritar14, isFavorito14);

        // URLs dos produtos
        String urlProduto11 = "https://www.amazon.com.br/PlayStation%C2%AE5-Slim-Edi%C3%A7%C3%A3o-Digital-Jogos/dp/B0CYJBWGH5/ref=asc_df_B0CYJBWGH5?mcid=24deaabda68e3b9aa3150dfa1ab41c61&tag=googleshopp00-20&linkCode=df0&hvadid=709884703642&hvpos=&hvnetw=g&hvrand=3682782172519802435&hvpone=&hvptwo=&hvqmt=&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9197305&hvtargid=pla-2300389685916&psc=1&language=pt_BR&gad_source=1";

        String urlProduto12 = "https://www.amazon.com.br/Samsung-Smartwatch-Galaxy-Classic-Grafite/dp/B0CCQP9JB7/ref=asc_df_B0CCQP9JB7?mcid=005ee85dc1a93678940be82921895baf&tag=googleshopp00-20&linkCode=df0&hvadid=709964503139&hvpos=&hvnetw=g&hvrand=12648078138559831829&hvpone=&hvptwo=&hvqmt=&hvdev=c&hvdvcmdl=&hvlocint=&hvlocphy=9197305&hvtargid=pla-2202862613217&language=pt_BR&gad_source=1&th=1";

        // Para os produtos 3 e 4 coloquei links fictícios, substitua pelos reais
        String urlProduto13 = "https://www.amazon.com.br/ExemploProduto13";
        String urlProduto14 = "https://www.amazon.com.br/ExemploProduto14";

        // Clique nos cards para abrir o link
        cardProduto11.setOnClickListener(v -> abrirLink(urlProduto11));
        cardProduto12.setOnClickListener(v -> abrirLink(urlProduto12));
        cardProduto13.setOnClickListener(v -> abrirLink(urlProduto13));
        cardProduto14.setOnClickListener(v -> abrirLink(urlProduto14));

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

        btnFavoritar13.setOnClickListener(v -> {
            isFavorito13 = !isFavorito13;
            prefs.edit().putBoolean("favorito_13", isFavorito13).apply();
            atualizarIcone(btnFavoritar13, isFavorito13);
        });

        btnFavoritar14.setOnClickListener(v -> {
            isFavorito14 = !isFavorito14;
            prefs.edit().putBoolean("favorito_14", isFavorito14).apply();
            atualizarIcone(btnFavoritar14, isFavorito14);
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
