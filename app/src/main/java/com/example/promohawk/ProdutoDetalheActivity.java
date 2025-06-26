package com.example.promohawk;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Preco;
import com.example.promohawk.model.Produto;
import com.example.promohawk.model.ProdutoUnicoResponse;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView textNome, textPreco, textMelhorPreco, textQtdAvaliacoes;
    private ImageView imgFavorito, btnVoltar;
    private Button btnIrLoja;
    private LineChart graficoPreco;
    private RatingBar ratingBar;
    private RecyclerView recyclerViewReviews;

    private boolean favorito = false;
    private ApiService apiService;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_detalhe);

        viewPager = findViewById(R.id.viewPagerImagemProduto);
        textNome = findViewById(R.id.textNomeProduto);
        textPreco = findViewById(R.id.textPrecoProduto);
        textMelhorPreco = findViewById(R.id.textMelhorPreco);
        textQtdAvaliacoes = findViewById(R.id.textQtdAvaliacoes);
        imgFavorito = findViewById(R.id.imgFavorito);
        btnVoltar = findViewById(R.id.btnVoltar); // botão de voltar
        ratingBar = findViewById(R.id.ratingBar);
        btnIrLoja = findViewById(R.id.btnIrLoja);
        graficoPreco = findViewById(R.id.graficoPreco);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);

        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        imgFavorito.setOnClickListener(v -> {
            favorito = !favorito;
            imgFavorito.setImageResource(favorito ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
        });

        btnVoltar.setOnClickListener(v -> finish());

        String idProdutoStr = getIntent().getStringExtra("idProduto");
        if (idProdutoStr != null) {
            try {
                int idProduto = Integer.parseInt(idProdutoStr);
                carregarProduto(idProduto);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void carregarProduto(int idProduto) {
        apiService.buscarProdutoPorId(idProduto).enqueue(new Callback<ProdutoUnicoResponse>() {
            @Override
            public void onResponse(Call<ProdutoUnicoResponse> call, Response<ProdutoUnicoResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getProduto() != null) {
                    Produto produto = response.body().getProduto();
                    atualizarUI(produto);
                } else {
                    Toast.makeText(ProdutoDetalheActivity.this, "Produto não encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdutoUnicoResponse> call, Throwable t) {
                Toast.makeText(ProdutoDetalheActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizarUI(Produto produto) {
        textNome.setText(produto.getNome());

        if (produto.getPrecos() != null && !produto.getPrecos().isEmpty()) {
            Preco preco = produto.getPrecos().get(0);
            textPreco.setText("R$ " + preco.getPreco());
            textMelhorPreco.setText("R$ " + preco.getValorParcela());
        } else {
            textPreco.setText("Preço indisponível");
            textMelhorPreco.setText("");
        }

        ratingBar.setRating(produto.getAvaliacao());
        textQtdAvaliacoes.setText(String.format("(%.1f estrelas)", produto.getAvaliacao()));

        if (produto.getImagens() != null && !produto.getImagens().isEmpty()) {
            viewPager.setOffscreenPageLimit(1);
            viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
            ImagemAdapter adapter = new ImagemAdapter(produto.getImagens());
            viewPager.setAdapter(adapter);
            int paddingPx = (int) (16 * getResources().getDisplayMetrics().density);
            viewPager.setPadding(paddingPx, 0, paddingPx, 0);
            viewPager.setClipToPadding(false);
        } else {
            viewPager.setAdapter(new ImagemAdapter(Collections.singletonList("URL_IMAGEM_PADRAO")));
        }

        if (produto.getHistoricoPrecos() != null && !produto.getHistoricoPrecos().isEmpty()) {
            List<Entry> entries = new ArrayList<>();
            for (int i = 0; i < produto.getHistoricoPrecos().size(); i++) {
                entries.add(new Entry(i, produto.getHistoricoPrecos().get(i)));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Histórico de Preço (R$)");
            dataSet.setColor(Color.parseColor("#1E88E5"));
            dataSet.setValueTextColor(Color.BLACK);
            dataSet.setLineWidth(2f);
            dataSet.setCircleRadius(4f);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(Color.parseColor("#BBDEFB"));
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            graficoPreco.setData(new LineData(dataSet));
            graficoPreco.getDescription().setEnabled(false);
            graficoPreco.getAxisRight().setEnabled(false);
            graficoPreco.getXAxis().setDrawGridLines(false);
            graficoPreco.getAxisLeft().setDrawGridLines(false);
            graficoPreco.invalidate();
        }

        if (produto.getReviews() != null && !produto.getReviews().isEmpty()) {
            recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
            reviewAdapter = new ReviewAdapter(produto.getReviews());
            recyclerViewReviews.setAdapter(reviewAdapter);
        }

        if (produto.getUrlLoja() != null && !produto.getUrlLoja().isEmpty()) {
            btnIrLoja.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(produto.getUrlLoja()));
                startActivity(intent);
            });
        }
    }
}
