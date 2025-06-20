package com.example.promohawk;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.promohawk.model.Produto;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView textNome, textPreco, textMelhorPreco, textQtdAvaliacoes;
    private ImageView imgFavorito;
    private Button btnVerOpcoes, btnIrLoja;
    private LineChart graficoPreco;
    private RatingBar ratingBar;

    private boolean favorito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_detalhe);

        viewPager = findViewById(R.id.viewPagerImagemProduto);
        textNome = findViewById(R.id.textNomeProduto);
        textPreco = findViewById(R.id.textPrecoProduto);
        imgFavorito = findViewById(R.id.imgFavorito);
        graficoPreco = findViewById(R.id.graficoPreco);
        textMelhorPreco = findViewById(R.id.textMelhorPreco);
        btnIrLoja = findViewById(R.id.btnIrLoja);
        ratingBar = findViewById(R.id.ratingBar);
        textQtdAvaliacoes = findViewById(R.id.textQtdAvaliacoes);

        imgFavorito.setOnClickListener(v -> {
            favorito = !favorito;
            imgFavorito.setImageResource(favorito ?
                    R.drawable.ic_favorite_filled :
                    R.drawable.ic_favorite_border);
        });

        String idProduto = getIntent().getStringExtra("idProduto");
        if (idProduto != null) {
            carregarProduto(idProduto);
        }
    }

    private void carregarProduto(String idProduto) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://suaapi.com/") // Atualize para sua API real
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProdutoService service = retrofit.create(ProdutoService.class);
        service.getProduto(idProduto).enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    atualizarUI(response.body());
                } else {
                    Toast.makeText(ProdutoDetalheActivity.this,
                            "Falha ao carregar produto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Toast.makeText(ProdutoDetalheActivity.this,
                        "Erro na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizarUI(Produto produto) {
        textNome.setText(produto.getNome());
        textPreco.setText(produto.getPreco());
        textMelhorPreco.setText(produto.getMelhorPreco());
        ratingBar.setRating(produto.getAvaliacao());
        textQtdAvaliacoes.setText("(" + produto.getQtdAvaliacoes() + " avaliações)");

        if (produto.getImagens() != null) {
            viewPager.setAdapter(new ImagemAdapter(produto.getImagens()));
        }

        List<Float> historico = produto.getHistoricoPrecos();
        if (historico != null && !historico.isEmpty()) {
            List<Entry> entries = new ArrayList<>();
            for (int i = 0; i < historico.size(); i++) {
                entries.add(new Entry(i, historico.get(i)));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Preço (R$)");
            dataSet.setColor(Color.BLUE);
            dataSet.setValueTextColor(Color.BLACK);

            graficoPreco.setData(new LineData(dataSet));
            graficoPreco.invalidate();
        }

        btnIrLoja.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(produto.getUrlLoja()));
            startActivity(intent);
        });
    }
}
