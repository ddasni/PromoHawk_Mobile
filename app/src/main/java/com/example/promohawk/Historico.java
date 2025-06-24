package com.example.promohawk;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class Historico extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private HistoricoAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico); // Certifique-se que esse é o nome correto do XML

        recyclerView = findViewById(R.id.recyclerViewHistorico);
        emptyView = findViewById(R.id.emptyHistorico);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        carregarHistorico();
    }

    private void carregarHistorico() {
        Call<List<Compra>> call = apiService.getHistoricoCompras();

        call.enqueue(new Callback<List<Compra>>() {
            @Override
            public void onResponse(Call<List<Compra>> call, Response<List<Compra>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Compra> compras = response.body();

                    if (compras.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        adapter = new HistoricoAdapter(compras);
                        recyclerView.setAdapter(adapter);
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(Historico.this, "Erro ao buscar histórico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Compra>> call, Throwable t) {
                Toast.makeText(Historico.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
