package com.example.promohawk;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.FavoritoViewHolder> {

    private List<ProdutoLocal> favoritos;
    private Context context;
    private SharedPreferences prefs;
    private OnFavoritoChangeListener listener;

    public interface OnFavoritoChangeListener {
        void onFavoritoChanged(ProdutoLocal produto);
    }

    public FavoritoAdapter(Context context, List<ProdutoLocal> favoritos, OnFavoritoChangeListener listener) {
        this.context = context;
        this.favoritos = favoritos;
        this.listener = listener;
        prefs = context.getSharedPreferences("favoritos_prefs", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorito, parent, false);
        return new FavoritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoViewHolder holder, int position) {
        ProdutoLocal produto = favoritos.get(position);

        holder.tvNomeProduto.setText(produto.nome);
        holder.tvPrecoAtual.setText(produto.preco);
        holder.tvMediaNota.setText(produto.nota);
        holder.imgProduto.setImageResource(produto.imagemResId);

        holder.btnFavoritar.setImageResource(
                produto.favorito ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border
        );

        holder.btnFavoritar.setOnClickListener(v -> {
            produto.favorito = !produto.favorito;
            holder.btnFavoritar.setImageResource(
                    produto.favorito ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border
            );
            prefs.edit().putBoolean("favorito_" + produto.id, produto.favorito).apply();

            if (listener != null) {
                listener.onFavoritoChanged(produto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritos.size();
    }

    static class FavoritoViewHolder extends RecyclerView.ViewHolder {
        ImageView btnFavoritar, imgProduto;
        TextView tvNomeProduto, tvPrecoAtual, tvMediaNota;

        public FavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            btnFavoritar = itemView.findViewById(R.id.btnFavoritar);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvPrecoAtual = itemView.findViewById(R.id.tvPrecoAtual);
            tvMediaNota = itemView.findViewById(R.id.tvMediaNota);
        }
    }
}
