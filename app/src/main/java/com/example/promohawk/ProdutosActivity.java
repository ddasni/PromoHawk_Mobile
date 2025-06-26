package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Produto;
import com.example.promohawk.model.ProdutoListResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutosActivity extends AppCompatActivity {

    private RecyclerView rvProdutos;
    private ProdutoAdapter produtoAdapter;
    private ApiService apiService;
    private Context context = this;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        rvProdutos = findViewById(R.id.rvProdutos);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        configurarBottomNavigation();
        carregarProdutos();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_produtos);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(context, Home.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_produtos) {
                return true;
            } else if (id == R.id.nav_cupons) {
                startActivity(new Intent(context, CuponsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_config) {
                startActivity(new Intent(context, Config.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_lojas) {
                startActivity(new Intent(context, Lojas.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }

            return false;
        });
    }

    private void carregarProdutos() {
        apiService.listarProdutos().enqueue(new Callback<ProdutoListResponse>() {
            @Override
            public void onResponse(Call<ProdutoListResponse> call, Response<ProdutoListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> listaProdutos = response.body().getProdutos();
                    if (listaProdutos == null || listaProdutos.isEmpty()) {
                        Toast.makeText(context, "Nenhum produto disponível.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    produtoAdapter = new ProdutoAdapter(context, listaProdutos, produto -> {
                        Intent intent = new Intent(context, ProdutoDetalheActivity.class);
                        intent.putExtra("idProduto", String.valueOf(produto.getId()));
                        startActivity(intent);
                    });

                    // ⬇️ Layout com 2 colunas
                    rvProdutos.setLayoutManager(new GridLayoutManager(context, 2));
                    rvProdutos.setAdapter(produtoAdapter);
                } else {
                    Toast.makeText(context, "Erro ao obter produtos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdutoListResponse> call, Throwable t) {
                Toast.makeText(context, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
