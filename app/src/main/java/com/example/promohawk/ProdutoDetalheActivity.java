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

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Produto;
import com.example.promohawk.model.ProdutoResponse;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView textNome, textPreco, textMelhorPreco, textQtdAvaliacoes;
    private ImageView imgFavorito;
    private Button btnIrLoja;
    private LineChart graficoPreco;
    private RatingBar ratingBar;

    private boolean favorito = false;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_detalhe);

        // Inicializa views
        viewPager = findViewById(R.id.viewPagerImagemProduto);
        textNome = findViewById(R.id.textNomeProduto);
        textPreco = findViewById(R.id.textPrecoProduto);
        textMelhorPreco = findViewById(R.id.textMelhorPreco);
        textQtdAvaliacoes = findViewById(R.id.textQtdAvaliacoes);
        imgFavorito = findViewById(R.id.imgFavorito);
        ratingBar = findViewById(R.id.ratingBar);
        btnIrLoja = findViewById(R.id.btnIrLoja);
        graficoPreco = findViewById(R.id.graficoPreco); // ✅ Inicializado aqui

        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        imgFavorito.setOnClickListener(v -> {
            favorito = !favorito;
            imgFavorito.setImageResource(favorito ?
                    R.drawable.ic_favorite_filled :
                    R.drawable.ic_favorite_border);
        });

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
        apiService.getProduto(idProduto).enqueue(new Callback<ProdutoResponse>() {
            @Override
            public void onResponse(Call<ProdutoResponse> call, Response<ProdutoResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getProduto() != null) {
                    atualizarUI(response.body().getProduto());
                } else {
                    Toast.makeText(ProdutoDetalheActivity.this,
                            "Produto não encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdutoResponse> call, Throwable t) {
                Toast.makeText(ProdutoDetalheActivity.this,
                        "Erro ao carregar produto: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void atualizarUI(Produto produto) {
        textNome.setText(produto.getNome());
        textPreco.setText(produto.getPreco());
        textMelhorPreco.setText(produto.getMelhorPreco());
        ratingBar.setRating(produto.getAvaliacao());
        textQtdAvaliacoes.setText("(" + produto.getQtdAvaliacoes() + " avaliações)");

        if (produto.getImagens() != null && !produto.getImagens().isEmpty()) {
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
            dataSet.setLineWidth(2f);
            dataSet.setCircleRadius(3f);
            dataSet.setDrawValues(false);

            graficoPreco.setData(new LineData(dataSet));
            graficoPreco.getDescription().setEnabled(false);
            graficoPreco.invalidate();
        }

        btnIrLoja.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(produto.getUrlLoja()));
            startActivity(intent);
        });
    }
}
