package com.example.promohawk;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.Categoria;
import com.example.promohawk.Produto;
import com.example.promohawk.Cupom;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.promohawk.api.ApiService;


public class Home extends AppCompatActivity {

    private RecyclerView recyclerViewProdutos;
    private ProdutoAdapter produtoAdapter;
    private List<Produto> listaProdutos = new ArrayList<>();

    private RecyclerView recyclerViewCupons;
    private CupomAdapter cupomAdapter;
    private List<Cupom> listaCupons = new ArrayList<>();

    private LinearLayout linearLayoutCategorias;
    private List<Categoria> listaCategorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        linearLayoutCategorias = findViewById(R.id.linearLayoutCategorias);

        configurarBotoes();
        configurarImageSlider();
        configurarRecyclerView();
        carregarProdutosDaApi();
        configurarRecyclerCupons();
        carregarCuponsDaApi();
        carregarCategoriasDaApi();
    }

    private void configurarBotoes() {
        findViewById(R.id.verMaisCategorias).setOnClickListener(v -> {
            startActivity(new Intent(Home.this, CategoriasDestaque.class));
        });
        findViewById(R.id.verMaisProdutos).setOnClickListener(v -> {
            startActivity(new Intent(Home.this, ProdutosDestaque.class));
        });
        findViewById(R.id.verMaisCupons).setOnClickListener(v -> {
            startActivity(new Intent(Home.this, Cupom.class));
        });

        findViewById(R.id.imgEletronicos).setOnClickListener(v -> abrirCategoria("Eletrônicos"));
        findViewById(R.id.imgVideoGames).setOnClickListener(v -> abrirCategoria("Video Games"));
        findViewById(R.id.imgSmartphones).setOnClickListener(v -> abrirCategoria("Smartphones"));
        findViewById(R.id.imgCalcados).setOnClickListener(v -> abrirCategoria("Calçados"));
        findViewById(R.id.imgLivros).setOnClickListener(v -> abrirCategoria("Livros"));

        ((TextView) findViewById(R.id.precoAntigoXboxS)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView) findViewById(R.id.precoAntigoXboxX)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView) findViewById(R.id.precoAntigoPS5)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView) findViewById(R.id.precoAntigoPS4)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void configurarImageSlider() {
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://picsum.photos/seed/picsum/200/300", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://picsum.photos/200/300?grayscale", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel("https://picsum.photos/200/300/?blur", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
        imageSlider.startSliding(2000);
    }

    // Home.java (completo, apenas a parte modificada foi alterada abaixo)

    private void configurarRecyclerView() {
        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        produtoAdapter = new ProdutoAdapter(this, listaProdutos, new ProdutoClickListener() {
            @Override
            public void onProdutoClick(Produto produto) {
                // Abrir ProdutoDetalheActivity passando o ID
                Intent intent = new Intent(Home.this, ProdutoDetalheActivity.class);
                intent.putExtra("idProduto", produto.getId()); // Certifique-se de que o ID esteja presente
                startActivity(intent);
            }
        });

        recyclerViewProdutos.setAdapter(produtoAdapter);
    }


    private void carregarProdutosDaApi() {
        ApiService apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);
        Call<List<Produto>> call = apiService.getProdutos();

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaProdutos.clear();
                    listaProdutos.addAll(response.body());
                    produtoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Toast.makeText(Home.this, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirCategoria(String nomeCategoria) {
        Toast.makeText(this, "Categoria: " + nomeCategoria, Toast.LENGTH_SHORT).show();
    }

    private void configurarRecyclerCupons() {
        recyclerViewCupons = findViewById(R.id.recyclerViewCupons);
        recyclerViewCupons.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        cupomAdapter = new CupomAdapter(this, listaCupons);
        recyclerViewCupons.setAdapter(cupomAdapter);
    }

    private void carregarCuponsDaApi() {
        ApiService apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);
        Call<List<Cupom>> call = apiService.getCupons();

        call.enqueue(new Callback<List<Cupom>>() {
            @Override
            public void onResponse(Call<List<Cupom>> call, Response<List<Cupom>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCupons.clear();
                    listaCupons.addAll(response.body());
                    cupomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Cupom>> call, Throwable t) {
                Toast.makeText(Home.this, "Erro ao carregar cupons", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void carregarCategoriasDaApi() {
        ApiService apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);
        Call<List<Categoria>> call = apiService.getCategorias();

        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCategorias.clear();
                    listaCategorias.addAll(response.body());
                    popularCategorias();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Toast.makeText(Home.this, "Erro ao carregar categorias", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void popularCategorias() {
        linearLayoutCategorias.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Categoria categoria : listaCategorias) {
            View card = inflater.inflate(R.layout.item_categoria, linearLayoutCategorias, false);

            TextView nomeCategoria = card.findViewById(R.id.txtNomeCategoria);
            ImageView imagemCategoria = card.findViewById(R.id.imgCategoria);

            nomeCategoria.setText(categoria.getNome());
            Glide.with(this).load(categoria.getImagemUrl()).placeholder(R.drawable.placeholder).into(imagemCategoria);

            card.setOnClickListener(v -> abrirCategoria(categoria.getNome()));

            linearLayoutCategorias.addView(card);
        }
    }
}