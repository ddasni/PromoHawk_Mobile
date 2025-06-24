package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Produto;
import com.example.promohawk.model.ProdutoListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosPorCategoriaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;
    private ProgressBar progressBar;
    private TextView tituloCategoria, txtSemResultados;

    private ApiService apiService;
    private List<Produto> listaFiltrada = new ArrayList<>();
    private String categoriaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_por_categoria);

        recyclerView = findViewById(R.id.recyclerViewCategoria);
        progressBar = findViewById(R.id.progressBarCategoria);
        tituloCategoria = findViewById(R.id.textTituloCategoria);
        txtSemResultados = findViewById(R.id.txtCategoriaVazia);

        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        categoriaSelecionada = getIntent().getStringExtra("categoria");
        tituloCategoria.setText("Categoria: " + categoriaSelecionada);

        carregarProdutosDaCategoria();
    }

    private void carregarProdutosDaCategoria() {
        progressBar.setVisibility(View.VISIBLE);

        apiService.getProduto().enqueue(new Callback<ProdutoListResponse>() {
            @Override
            public void onResponse(Call<ProdutoListResponse> call, Response<ProdutoListResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> todos = response.body().getProdutos();
                    listaFiltrada.clear();

                    for (Produto produto : todos) {
                        if (produto.getCategoria() != null &&
                                produto.getCategoria().getNome().equalsIgnoreCase(categoriaSelecionada)) {
                            listaFiltrada.add(produto);
                        }
                    }

                    if (listaFiltrada.isEmpty()) {
                        txtSemResultados.setVisibility(View.VISIBLE);
                    } else {
                        txtSemResultados.setVisibility(View.GONE);

                        produtoAdapter = new ProdutoAdapter(ProdutosPorCategoriaActivity.this, listaFiltrada, produto -> {
                            Intent intent = new Intent(ProdutosPorCategoriaActivity.this, ProdutoDetalheActivity.class);
                            intent.putExtra("idProduto", String.valueOf(produto.getId()));
                            startActivity(intent);
                        });

                        recyclerView.setLayoutManager(new LinearLayoutManager(ProdutosPorCategoriaActivity.this));
                        recyclerView.setAdapter(produtoAdapter);
                    }
                } else {
                    txtSemResultados.setVisibility(View.VISIBLE);
                    Toast.makeText(ProdutosPorCategoriaActivity.this, "Nenhum produto encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdutoListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProdutosPorCategoriaActivity.this, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
