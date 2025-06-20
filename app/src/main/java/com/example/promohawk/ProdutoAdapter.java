package com.example.promohawk;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.promohawk.model.Produto;

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

        // Preço antigo riscado
        String precoAntigo = produto.getMelhorPreco();
        if (precoAntigo != null && !precoAntigo.isEmpty()) {
            holder.tvPrecoAntigo.setVisibility(View.VISIBLE);
            holder.tvPrecoAntigo.setText(precoAntigo);
            holder.tvPrecoAntigo.setPaintFlags(
                    holder.tvPrecoAntigo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );
        } else {
            holder.tvPrecoAntigo.setVisibility(View.GONE);
        }

        // Avaliação
        float avaliacao = produto.getAvaliacao();
        if (avaliacao > 0) {
            holder.tvAvaliacao.setText("★ " + String.format("%.1f", avaliacao));
        } else {
            holder.tvAvaliacao.setText("Sem avaliação");
        }

        // Carregar imagem com Glide
        Glide.with(context)
                .load(produto.getImagemUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(holder.imgProduto);

        // Clique no item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProdutoClick(produto);
            }
        });

        // Clique no botão de favoritar
        holder.btnFavoritar.setOnClickListener(v -> {
            // Implemente aqui a lógica de favoritar, se necessário
        });
    }

    @Override
    public int getItemCount() {
        return produtos != null ? produtos.size() : 0;
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
