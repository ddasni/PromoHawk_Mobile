package com.example.promohawk;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favoritos extends AppCompatActivity {

    private RecyclerView rvFavoritos;
    private FavoritosAdapter adapter;
    private ArrayList<Produto_Favorito> listaFavoritos;
    private ImageView botaoVoltar; // Adicionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        // Inicializa as views
        rvFavoritos = findViewById(R.id.recyclerViewFavoritos);
        botaoVoltar = findViewById(R.id.botaoVoltarFavoritos); // Adicionado

        // Configura o RecyclerView
        listaFavoritos = new ArrayList<>();
        adapter = new FavoritosAdapter(listaFavoritos);
        rvFavoritos.setLayoutManager(new LinearLayoutManager(this));
        rvFavoritos.setAdapter(adapter);

        // Configura o botão de voltar
        botaoVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Favoritos.this, Config.class); // Ou para a Activity apropriada
            startActivity(intent);
            finish();
        });

        carregarFavoritos();
    }

    private void carregarFavoritos() {
        ApiService apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("usuario_prefs", MODE_PRIVATE);
        String token = prefs.getString("token", null);

        Call<List<Produto_Favorito>> call = apiService.getFavoritos(token);
        call.enqueue(new Callback<List<Produto_Favorito>>() {
            @Override
            public void onResponse(Call<List<Produto_Favorito>> call, Response<List<Produto_Favorito>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaFavoritos.clear();
                    listaFavoritos.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Produto_Favorito>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}