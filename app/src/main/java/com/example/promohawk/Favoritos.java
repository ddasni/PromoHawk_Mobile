package com.example.promohawk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Favoritos extends AppCompatActivity {

    private RecyclerView rvFavoritos;
    private FavoritosAdapter adapter; // Corrigido aqui para usar o adapter certo
    private ArrayList<String> listaFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        rvFavoritos = findViewById(R.id.rvFavoritos);

        // Dados iniciais (pode começar vazio)
        listaFavoritos = new ArrayList<>();

        // Configura RecyclerView
        adapter = new FavoritosAdapter(listaFavoritos); // Usando o adapter certo
        rvFavoritos.setLayoutManager(new LinearLayoutManager(this));
        rvFavoritos.setAdapter(adapter);
    }

    // Método para adicionar um favorito e atualizar a lista
    public void adicionarFavorito(String item) {
        listaFavoritos.add(item);
        adapter.notifyItemInserted(listaFavoritos.size() - 1);
    }
}
