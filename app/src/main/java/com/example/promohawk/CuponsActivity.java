package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.ClipData;
import android.content.ClipboardManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.Cupom;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuponsActivity extends AppCompatActivity {

    private GridLayout gridCupons;
    private ApiService apiService;
    private BottomNavigationView bottomNavigationView;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupom);

        gridCupons = findViewById(R.id.gridCupons);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        configurarBottomNavigation();
        carregarCupons();
    }

    private void configurarBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.nav_cupons);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(context, Home.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_produtos) {
                startActivity(new Intent(context, ProdutosActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_cupons) {
                return true;
            } else if (id == R.id.nav_config) {
                startActivity(new Intent(context, Config.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void carregarCupons() {
        apiService.getCupons().enqueue(new Callback<List<Cupom>>() {
            @Override
            public void onResponse(Call<List<Cupom>> call, Response<List<Cupom>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exibirCupons(response.body());
                } else {
                    Toast.makeText(CuponsActivity.this, "Nenhum cupom encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cupom>> call, Throwable t) {
                Toast.makeText(CuponsActivity.this, "Erro ao carregar cupons", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exibirCupons(List<Cupom> listaCupons) {
        gridCupons.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Cupom cupom : listaCupons) {
            View card = inflater.inflate(R.layout.cupom_item, gridCupons, false);

            TextView tvData = card.findViewById(R.id.tvData);
            TextView tvDesconto = card.findViewById(R.id.tvDesconto);
            TextView tvDescricao = card.findViewById(R.id.tvDescricao);
            TextView tvVerCodigo = card.findViewById(R.id.tvVerCodigo);
            View layoutCodigo = card.findViewById(R.id.layoutCodigo);
            TextView tvCodigo = card.findViewById(R.id.tvCodigo);
            Button btnCopiar = card.findViewById(R.id.btnCopiar);

            tvData.setText(cupom.getData());
            tvDesconto.setText(cupom.getDesconto());
            tvDescricao.setText(cupom.getDescricao());
            tvCodigo.setText(cupom.getCodigo());

            layoutCodigo.setVisibility(View.GONE);

            tvVerCodigo.setOnClickListener(v -> {
                if (layoutCodigo.getVisibility() == View.GONE) {
                    layoutCodigo.setVisibility(View.VISIBLE);
                    tvVerCodigo.setText("Esconder código →");
                } else {
                    layoutCodigo.setVisibility(View.GONE);
                    tvVerCodigo.setText("Ver código →");
                }
            });

            btnCopiar.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Código do Cupom", cupom.getCodigo());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Código copiado!", Toast.LENGTH_SHORT).show();
            });

            gridCupons.addView(card);
        }
    }
}
