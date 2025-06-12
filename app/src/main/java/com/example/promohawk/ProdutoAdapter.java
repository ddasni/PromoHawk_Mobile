package com.example.promohawk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private Context context;
    private List<Produto> produtos;
    private ProdutoClickListener listener;

    public ProdutoAdapter(Context context, List<Produto> produtos, ProdutoClickListener listener) {
        this.context = context;
        this.produtos = produtos;
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
        Produto produto = produtos.get(position);

        holder.tvNomeProduto.setText(produto.getNome());
        holder.tvPrecoAtual.setText(produto.getPreco());
        holder.tvPrecoAntigo.setText(produto.getMelhorPreco());
        holder.tvAvaliacao.setText("★ " + produto.getAvaliacao());

        // Deixa o texto do preço antigo riscado
        holder.tvPrecoAntigo.setPaintFlags(holder.tvPrecoAntigo.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(context)
                .load(produto.getImagemUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.imgProduto);

        // Clique no produto
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProdutoClick(produto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto;
        TextView tvNomeProduto, tvPrecoAtual, tvPrecoAntigo, tvAvaliacao;
        ImageView btnFavoritar;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvPrecoAtual = itemView.findViewById(R.id.tvPrecoAtual);
            tvPrecoAntigo = itemView.findViewById(R.id.tvPrecoAntigo);
            tvAvaliacao = itemView.findViewById(R.id.tvAvaliacao);
            btnFavoritar = itemView.findViewById(R.id.btnFavoritar);
        }
    }
}
