package com.example.promohawk;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.promohawk.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends AppCompatActivity {

    private RecyclerView recyclerViewProdutos;
    private ProdutoAdapter produtoAdapter;
    private List<Produto> listaProdutos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        configurarBotoes();
        configurarImageSlider();
        configurarRecyclerView();
        carregarProdutosDaApi();
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

    private void configurarRecyclerView() {
        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        produtoAdapter = new ProdutoAdapter(this, listaProdutos);
        recyclerViewProdutos.setAdapter(produtoAdapter);
    }

    private void carregarProdutosDaApi() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
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
}

