package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritoViewHolder> {

    private List<Produto_Favorito> favoritos;

    public FavoritosAdapter(List<Produto_Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    @NonNull
    @Override
    public FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorito, parent, false);
        return new FavoritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoViewHolder holder, int position) {
        Produto_Favorito favorito = favoritos.get(position);
        holder.txtNome.setText(favorito.getNome());

        // Clique no botão de favoritar (teste visual por enquanto)
        holder.btnFavoritar.setOnClickListener(v -> {
            holder.btnFavoritar.setImageResource(R.drawable.ic_favorite_preenchido_preto); // precisa ter esse ícone

            // Vai para a tela de favoritos (já está nela, então por enquanto pode só mostrar um toast ou log)
            Context context = v.getContext();
            Intent intent = new Intent(context, Favoritos.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    static class FavoritoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        ImageButton btnFavoritar;

        public FavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeFavorito);
            btnFavoritar = itemView.findViewById(R.id.btnFavoritar);
        }
    }
}
