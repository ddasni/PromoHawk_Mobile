package com.example.promohawk;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.adapter.HistoricoAdapter;
import com.example.promohawk.api.ApiService;
import com.example.promohawk.model.Compra;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoRE extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private HistoricoAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);  // seu layout principal

        recyclerView = findViewById(R.id.recyclerViewHistorico);
        emptyView = findViewById(R.id.emptyHistorico);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = Amazon.RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        carregarHistorico();
    }

    private void carregarHistorico() {
        Call<List<Compra>> call = apiService.getHistoricoCompras();

        call.enqueue(new Callback<List<Compra>>() {
            @Override
            public void onResponse(Call<List<Compra>> call, Response<List<Compra>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    adapter = new HistoricoAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Compra>> call, Throwable t) {
                Toast.makeText(HistoricoRE.this, "Erro ao carregar histórico", Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        });
    }
}
