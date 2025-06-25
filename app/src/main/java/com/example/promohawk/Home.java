package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private ApiService apiService;
    private LinearLayout linearLayoutCategorias;
    private RecyclerView recyclerViewProdutos, recyclerViewCupons;
    private ProdutoAdapter produtoAdapter;
    private CupomAdapter cupomAdapter;
    private ProgressBar progressBarProdutos, progressBarCupons;
    private TextView txtProdutosVazio, txtCuponsVazio;
    private BottomNavigationView bottomNavigationView;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        linearLayoutCategorias = findViewById(R.id.linearLayoutCategorias);
        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        recyclerViewCupons = findViewById(R.id.recyclerViewCupons);
        txtProdutosVazio = findViewById(R.id.txtProdutosVazio);
        txtCuponsVazio = findViewById(R.id.txtCuponsVazio);
        progressBarProdutos = findViewById(R.id.progressBarProdutos);
        progressBarCupons = findViewById(R.id.progressBarCupons);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        configurarBottomNavigation();
        carregarSlider();
        carregarCategorias();
        carregarProdutos();
        carregarCupons();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) return true;
            if (id == R.id.nav_produtos) startActivity(new Intent(context, ProdutosActivity.class));
            else if (id == R.id.nav_cupons) startActivity(new Intent(context, CuponsActivity.class));
            else if (id == R.id.nav_config) startActivity(new Intent(context, Config.class));
            else if (id == R.id.nav_lojas) startActivity(new Intent(context, Lojas.class));
            overridePendingTransition(0, 0);
            finish();
            return true;
        });
    }

    private void carregarSlider() {
        ImageSlider slider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://link-imagem-1.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://link-imagem-2.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://link-imagem-3.png", ScaleTypes.FIT));
        slider.setImageList(slideModels, ScaleTypes.FIT);
    }

    private void carregarCategorias() {
        apiService.listarCategorias().enqueue(new Callback<CategoriaListResponse>() {
            @Override
            public void onResponse(Call<CategoriaListResponse> call, Response<CategoriaListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Categoria> categorias = response.body().getCategorias();
                    linearLayoutCategorias.removeAllViews();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    int limite = Math.min(6, categorias.size());

                    for (int i = 0; i < limite; i++) {
                        Categoria categoria = categorias.get(i);
                        View view = inflater.inflate(R.layout.item_categoria, linearLayoutCategorias, false);
                        TextView nome = view.findViewById(R.id.nomeCategoria);
                        ImageView imagem = view.findViewById(R.id.imgCategoria);
                        nome.setText(categoria.getNome());

                        Glide.with(context)
                                .load(categoria.getImagem())
                                .placeholder(R.drawable.placeholder)
                                .circleCrop() // borda redonda
                                .into(imagem);

                        view.setOnClickListener(v -> {
                            Intent intent = new Intent(context, ProdutosPorCategoriaActivity.class);
                            intent.putExtra("categoria", categoria.getNome());
                            startActivity(intent);
                        });

                        linearLayoutCategorias.addView(view);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoriaListResponse> call, Throwable t) {
                Toast.makeText(context, "Erro ao carregar categorias", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarProdutos() {
        progressBarProdutos.setVisibility(View.VISIBLE);
        apiService.listarProdutos().enqueue(new Callback<ProdutoListResponse>() {
            @Override
            public void onResponse(Call<ProdutoListResponse> call, Response<ProdutoListResponse> response) {
                progressBarProdutos.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> lista = response.body().getProdutos();
                    if (lista.isEmpty()) {
                        txtProdutosVazio.setVisibility(View.VISIBLE);
                    } else {
                        txtProdutosVazio.setVisibility(View.GONE);
                        produtoAdapter = new ProdutoAdapter(context, lista.subList(0, Math.min(6, lista.size())), produto -> {
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
            public void onFailure(Call<ProdutoListResponse> call, Throwable t) {
                progressBarProdutos.setVisibility(View.GONE);
                Log.e("Home", "Erro ao carregar produtos: ", t);
                Toast.makeText(context, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void carregarCupons() {
        progressBarCupons.setVisibility(View.VISIBLE);
        apiService.listarCupons().enqueue(new Callback<CupomListResponse>() {
            @Override
            public void onResponse(Call<CupomListResponse> call, Response<CupomListResponse> response) {
                progressBarCupons.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Cupom> lista = response.body().getCupons();
                    if (lista.isEmpty()) {
                        txtCuponsVazio.setVisibility(View.VISIBLE);
                    } else {
                        txtCuponsVazio.setVisibility(View.GONE);
                        cupomAdapter = new CupomAdapter(context, lista.subList(0, Math.min(6, lista.size())));
                        recyclerViewCupons.setLayoutManager(new GridLayoutManager(context, 2));
                        recyclerViewCupons.setAdapter(cupomAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CupomListResponse> call, Throwable t) {
                progressBarCupons.setVisibility(View.GONE);
                Toast.makeText(context, "Erro ao carregar cupons", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
