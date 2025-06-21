package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Categoria;
import com.example.promohawk.model.Cupom;
import com.example.promohawk.model.Produto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private LinearLayout linearLayoutCategorias;
    private RecyclerView recyclerViewProdutos, recyclerViewCupons;
    private ProgressBar progressBarProdutos, progressBarCupons;
    private TextView txtProdutosVazio, txtCuponsVazio;
    private BottomNavigationView bottomNavigationView;

    private ProdutoAdapter produtoAdapter;
    private CupomAdapter cupomAdapter;
    private ApiService apiService;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        linearLayoutCategorias = findViewById(R.id.linearLayoutCategorias);
        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        recyclerViewCupons = findViewById(R.id.recyclerViewCupons);
        progressBarProdutos = findViewById(R.id.progressBarProdutos);
        progressBarCupons = findViewById(R.id.progressBarCupons);
        txtProdutosVazio = findViewById(R.id.txtProdutosVazio);
        txtCuponsVazio = findViewById(R.id.txtCuponsVazio);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        configurarBottomNavigation();
        configurarSlider();
        carregarCategorias();
        carregarProdutos();
        carregarCupons();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // Já está na Home, não faz nada
                return true;
            } else if (id == R.id.nav_produtos) {
                startActivity(new Intent(context, ProdutosActivity.class));
                overridePendingTransition(0, 0);
                finish();  // Finaliza para evitar pilha excessiva
                return true;
            } else if (id == R.id.nav_cupons) {
                startActivity(new Intent(context,  CuponsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_config) {
                startActivity(new Intent(context, Config.class));
                overridePendingTransition(0, 0);
                finish();
            }    else if (id == R.id.nav_lojas) {
                  startActivity(new Intent(context, Lojas.class));
                  overridePendingTransition(0, 0);
                  finish();
                  return true;
            }

                return false;

        });


    }

    private void configurarSlider() {
        ImageSlider slider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slider2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slider3, ScaleTypes.CENTER_CROP));
        slider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
    }

    private void carregarCategorias() {
        apiService.getCategorias().enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    linearLayoutCategorias.removeAllViews();
                    for (Categoria categoria : response.body()) {
                        View card = getLayoutInflater().inflate(R.layout.item_categoria, null);

                        TextView nomeCategoria = card.findViewById(R.id.nomeCategoria);
                        nomeCategoria.setText(categoria.getNome());

                        card.setOnClickListener(v -> {
                            Intent intent = new Intent(context, ProdutosPorCategoriaActivity.class);
                            intent.putExtra("categoria", categoria.getNome());
                            startActivity(intent);
                        });

                        linearLayoutCategorias.addView(card);
                    }
                } else {
                    Toast.makeText(context, "Nenhuma categoria encontrada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Toast.makeText(context, "Erro ao carregar categorias", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarProdutos() {
        progressBarProdutos.setVisibility(View.VISIBLE);
        apiService.getProdutos().enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                progressBarProdutos.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> lista = response.body();
                    if (lista.isEmpty()) {
                        txtProdutosVazio.setVisibility(View.VISIBLE);
                    } else {
                        txtProdutosVazio.setVisibility(View.GONE);
                        produtoAdapter = new ProdutoAdapter(context, lista, produto -> {
                            Intent intent = new Intent(context, ProdutoDetalheActivity.class);
                            intent.putExtra("idProduto", String.valueOf(produto.getId()));
                            startActivity(intent);
                        });
                        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        recyclerViewProdutos.setAdapter(produtoAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                progressBarProdutos.setVisibility(View.GONE);
                Toast.makeText(context, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarCupons() {
        progressBarCupons.setVisibility(View.VISIBLE);
        apiService.getCupons().enqueue(new Callback<List<Cupom>>() {
            @Override
            public void onResponse(Call<List<Cupom>> call, Response<List<Cupom>> response) {
                progressBarCupons.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Cupom> lista = response.body();
                    if (lista.isEmpty()) {
                        txtCuponsVazio.setVisibility(View.VISIBLE);
                    } else {
                        txtCuponsVazio.setVisibility(View.GONE);
                        cupomAdapter = new CupomAdapter(context, lista);
                        recyclerViewCupons.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        recyclerViewCupons.setAdapter(cupomAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cupom>> call, Throwable t) {
                progressBarCupons.setVisibility(View.GONE);
                Toast.makeText(context, "Erro ao carregar cupons", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
