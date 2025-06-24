package com.example.promohawk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.promohawk.model.Preco;
import com.example.promohawk.model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private final Context context;
    private final List<Produto> listaProdutos;
    private final ProdutoClickListener listener;

    public interface ProdutoClickListener {
        void onProdutoClick(Produto produto);
    }

    public ProdutoAdapter(Context context, List<Produto> produtos, ProdutoClickListener listener) {
        this.context = context;
        this.listaProdutos = produtos.size() > 6 ? produtos.subList(0, 6) : produtos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.tvNomeProduto.setText(produto.getNome());
        holder.ratingBarProduto.setRating(produto.getAvaliacao());

        if (produto.getPrecos() != null && !produto.getPrecos().isEmpty()) {
            Preco preco = produto.getPrecos().get(0);
            holder.tvPrecoAtual.setText(String.format("R$ %.2f", preco.getPreco()));
        } else {
            holder.tvPrecoAtual.setText("Preço indisponível");
        }

        Glide.with(context)
                .load(produto.getImagem())
                .placeholder(R.drawable.placeholder)
                .into(holder.imgProduto);

        holder.itemView.setOnClickListener(v -> listener.onProdutoClick(produto));
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto, btnFavoritar;
        TextView tvNomeProduto, tvPrecoAtual;
        RatingBar ratingBarProduto;


        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvPrecoAtual = itemView.findViewById(R.id.tvPrecoAtual);
            ratingBarProduto = itemView.findViewById(R.id.ratingBarProduto);
            btnFavoritar = itemView.findViewById(R.id.btnFavoritar);
        }
    }
}
