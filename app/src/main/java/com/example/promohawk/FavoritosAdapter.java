package com.example.promohawk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritoViewHolder> {

    private List<String> favoritos;

    public FavoritosAdapter(List<String> favoritos) {
        this.favoritos = favoritos;
    }

    @NonNull
    @Override
    public FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FavoritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoViewHolder holder, int position) {
        String favorito = favoritos.get(position);
        holder.tvItem.setText(favorito);
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    static class FavoritoViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public FavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }
    }
}
