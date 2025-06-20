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
        apiService.getProdutos().enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    listaFiltrada.clear();
                    for (Produto produto : response.body()) {
                        if (produto.getCategoria() != null &&
                                produto.getCategoria().equalsIgnoreCase(categoriaSelecionada)) {
                            listaFiltrada.add(produto);
                        }
                    }

                    if (listaFiltrada.isEmpty()) {
                        txtSemResultados.setVisibility(View.VISIBLE);
                    } else {
                        txtSemResultados.setVisibility(View.GONE);

                        // Criando o adapter com o listener de clique
                        produtoAdapter = new ProdutoAdapter(ProdutosPorCategoriaActivity.this, listaFiltrada, produto -> {
                            // Ação ao clicar no produto: abrir ProdutoDetalheActivity
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
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProdutosPorCategoriaActivity.this, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
