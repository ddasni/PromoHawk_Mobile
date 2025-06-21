package com.example.promohawk;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Lojas extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Loja> listaLojas = Arrays.asList(
            new Loja("Amazon", R.drawable.amazon, Amazon.class),
            new Loja("Centauro", R.drawable.centauru, Centauro.class),
            new Loja("Kabum", R.drawable.kabum, Kabum.class),
            new Loja("Pichau", R.drawable.pichau3, Pichau.class),
            new Loja("Magazine", R.drawable.magazine2, Magazine.class),
            new Loja("Mercado Livre", R.drawable.mercado2, Mercado_Livre.class)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lojas);

        recyclerView = findViewById(R.id.recyclerLojas);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new LojaAdapter(listaLojas));

        recyclerView.setLayoutAnimation(
                AnimationUtils.loadLayoutAnimation(this, R.anim.animation_fall_down)
        );
    }

    // Classe Loja com referência à Activity
    static class Loja {
        String nome;
        int imagemRes;
        Class<?> activityClass;

        Loja(String nome, int imagemRes, Class<?> activityClass) {
            this.nome = nome;
            this.imagemRes = imagemRes;
            this.activityClass = activityClass;
        }
    }

    public static class LojaAdapter extends RecyclerView.Adapter<LojaAdapter.LojaViewHolder> {

        private final List<Loja> lojas;

        public LojaAdapter(List<Loja> lojas) {
            this.lojas = lojas;
        }

        @NonNull
        @Override
        public LojaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loja, parent, false);
            return new LojaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LojaViewHolder holder, int position) {
            Loja loja = lojas.get(position);
            holder.txtNome.setText(loja.nome);
            holder.imgIcon.setImageResource(loja.imagemRes);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), loja.activityClass);
                v.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return lojas.size();
        }

        class LojaViewHolder extends RecyclerView.ViewHolder {
            TextView txtNome;
            ImageView imgIcon;

            public LojaViewHolder(@NonNull View itemView) {
                super(itemView);
                txtNome = itemView.findViewById(R.id.txtNomeLoja);
                imgIcon = itemView.findViewById(R.id.imgIconLoja);
            }
        }
    }
}
