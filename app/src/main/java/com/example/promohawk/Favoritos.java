package com.example.promohawk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Favoritos extends AppCompatActivity {

    private RecyclerView rvFavoritos;
    private FavoritosAdapter adapter;
    private ArrayList<String> listaFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        rvFavoritos = findViewById(R.id.recyclerViewFavoritos);

        listaFavoritos = new ArrayList<>();

        adapter = new FavoritosAdapter(listaFavoritos);
        rvFavoritos.setLayoutManager(new LinearLayoutManager(this));
        rvFavoritos.setAdapter(adapter);
    }

    public void adicionarFavorito(String item) {
        if (!listaFavoritos.contains(item)) {
            listaFavoritos.add(item);
            adapter.notifyItemInserted(listaFavoritos.size() - 1);
        }
    }
}
