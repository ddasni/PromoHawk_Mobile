package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Cupom;
import com.example.promohawk.model.CupomListResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuponsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCupons;
    private CupomAdapter cupomAdapter;
    private BottomNavigationView bottomNavigationView;
    private ApiService apiService;
    private Context context = this;

    private ProgressBar progressBarCupons;
    private TextView txtCuponsVazio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupom);

        // Referências
        recyclerViewCupons = findViewById(R.id.gridCupons);
        recyclerViewCupons.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewCupons.setHasFixedSize(true);

        progressBarCupons = findViewById(R.id.progressBarCupons);
        txtCuponsVazio = findViewById(R.id.txtCuponsVazio);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        configurarBottomNavigation();

        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);
        carregarCupons();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_cupons);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(context, Home.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (id == R.id.nav_produtos) {
                startActivity(new Intent(context, ProdutosActivity.class));
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
            } else if (id == R.id.nav_cupons) {
                return true; // já estamos nessa tela
            }
            return false;
        });
    }

    private void carregarCupons() {
        progressBarCupons.setVisibility(View.VISIBLE);
        txtCuponsVazio.setVisibility(View.GONE);

        apiService.listarCupons().enqueue(new Callback<CupomListResponse>() {
            @Override
            public void onResponse(Call<CupomListResponse> call, Response<CupomListResponse> response) {
                progressBarCupons.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Cupom> lista = response.body().getCupons();
                    if (lista.isEmpty()) {
                        txtCuponsVazio.setVisibility(View.VISIBLE);
                    } else {
                        cupomAdapter = new CupomAdapter(context, lista);
                        recyclerViewCupons.setAdapter(cupomAdapter);
                    }
                } else {
                    txtCuponsVazio.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Erro ao carregar cupons", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CupomListResponse> call, Throwable t) {
                progressBarCupons.setVisibility(View.GONE);
                txtCuponsVazio.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
