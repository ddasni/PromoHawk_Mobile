package com.example.promohawk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public class Favoritos extends AppCompatActivity {

    private RecyclerView recyclerFavoritos;
    private FavoritoAdapter adapter;
    private ArrayList<ProdutoLocal> todosProdutos;
    private ArrayList<ProdutoLocal> produtosFavoritos;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        prefs = getSharedPreferences("favoritos_prefs", MODE_PRIVATE);

        recyclerFavoritos = findViewById(R.id.recyclerViewFavoritos);
        recyclerFavoritos.setLayoutManager(new LinearLayoutManager(this));

        todosProdutos = new ArrayList<>();
        todosProdutos.add(new ProdutoLocal(11, "PlayStation 5 Slim Edição Digital", "R$ 3.999,99", "★ 4,5", false, R.drawable.playstation_5));
        todosProdutos.add(new ProdutoLocal(12, "Samsung Galaxy Watch6 Classic", "R$ 1.399,90", "★ 4,8", false, R.drawable.produto_watch6_classic));
        // adicione mais produtos se quiser

        carregarFavoritos();

        adapter = new FavoritoAdapter(this, produtosFavoritos, this::atualizarFavoritos);
        recyclerFavoritos.setAdapter(adapter);

        mostrarMensagemVazio();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarFavoritos();
        adapter.notifyDataSetChanged();
        mostrarMensagemVazio();
    }

    private void carregarFavoritos() {
        produtosFavoritos = new ArrayList<>();
        for (ProdutoLocal p : todosProdutos) {
            boolean fav = prefs.getBoolean("favorito_" + p.id, false);
            if (fav) {
                p.favorito = true;
                produtosFavoritos.add(p);
            }
        }
    }

    private void mostrarMensagemVazio() {
        View vazio = findViewById(R.id.emptyFavoritos);
        if (produtosFavoritos.isEmpty()) {
            vazio.setVisibility(View.VISIBLE);
            recyclerFavoritos.setVisibility(View.GONE);
        } else {
            vazio.setVisibility(View.GONE);
            recyclerFavoritos.setVisibility(View.VISIBLE);
        }
    }

    private void atualizarFavoritos(ProdutoLocal produto) {
        if (!produto.favorito) {
            Iterator<ProdutoLocal> it = produtosFavoritos.iterator();
            while (it.hasNext()) {
                ProdutoLocal p = it.next();
                if (p.id == produto.id) {
                    it.remove();
                    break;
                }
            }
            adapter.notifyDataSetChanged();
            mostrarMensagemVazio();
        }
    }
}
