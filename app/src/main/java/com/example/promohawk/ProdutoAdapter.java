package com.example.promohawk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private List<Produto> lista;
    private Context context;
    private Favoritos favoritosActivity;

    public ProdutoAdapter(Context context, List<Produto> lista, Favoritos favoritosActivity) {
        this.context = context;
        this.lista = lista;
        this.favoritosActivity = favoritosActivity;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto Produto = lista.get(position);
        holder.tvNome.setText(Produto.getNome());
        holder.imgProduto.setImageResource(Produto.getImagem());

        holder.btnFavoritar.setOnClickListener(v -> {
            // Ao clicar, adiciona ao favoritos
            if (favoritosActivity != null) {
                favoritosActivity.adicionarFavorito(Produto.getNome());
                Toast.makeText(context, "Adicionado aos favoritos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        ImageView imgProduto, btnFavoritar;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeProduto);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            btnFavoritar = itemView.findViewById(R.id.btnFavoritar);
        }
    }
}
