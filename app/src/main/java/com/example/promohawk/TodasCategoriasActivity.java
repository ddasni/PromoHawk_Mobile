package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Categoria;
import com.example.promohawk.model.CategoriaListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodasCategoriasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategorias;
    private ApiService apiService;
    private Context context;
    private ImageView btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_categorias);

        context = this;
        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        recyclerViewCategorias = findViewById(R.id.recyclerViewTodasCategorias);
        btnVoltar = findViewById(R.id.btnVoltar);

        recyclerViewCategorias.setLayoutManager(new GridLayoutManager(this, 3));

        btnVoltar.setOnClickListener(v -> finish());

        carregarCategorias();
    }

    private void carregarCategorias() {
        apiService.listarCategorias().enqueue(new Callback<CategoriaListResponse>() {
            @Override
            public void onResponse(Call<CategoriaListResponse> call, Response<CategoriaListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Categoria> lista = response.body().getCategorias();
                    recyclerViewCategorias.setAdapter(new CategoriaGridAdapter(context, lista));
                } else {
                    Toast.makeText(context, "Erro ao carregar categorias", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoriaListResponse> call, Throwable t) {
                Toast.makeText(context, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
