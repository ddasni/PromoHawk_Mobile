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
import com.example.promohawk.model.Preco;
import com.example.promohawk.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private final Context context;
    private final ProdutoClickListener listener;

    private final List<Produto> listaOriginal;
    private final List<Produto> listaProdutos;

    public interface ProdutoClickListener {
        void onProdutoClick(Produto produto);
    }

    public ProdutoAdapter(Context context, List<Produto> produtos, ProdutoClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.listaOriginal = new ArrayList<>(produtos);
        this.listaProdutos = new ArrayList<>(produtos);
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
        holder.tvMediaNota.setText("★ " + String.format("%.1f", produto.getAvaliacao()));

        if (produto.getPrecos() != null && !produto.getPrecos().isEmpty()) {
            Preco preco = produto.getPrecos().get(0);
            holder.tvPrecoAtual.setText("R$ " + preco.getPreco());
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

    public void filtrarPorNome(String texto) {
        listaProdutos.clear();
        if (texto == null || texto.trim().isEmpty()) {
            listaProdutos.addAll(listaOriginal);
        } else {
            String query = texto.toLowerCase();
            for (Produto produto : listaOriginal) {
                if (produto.getNome().toLowerCase().contains(query)) {
                    listaProdutos.add(produto);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduto, btnFavoritar;
        TextView tvNomeProduto, tvPrecoAtual, tvMediaNota;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            tvNomeProduto = itemView.findViewById(R.id.tvNomeProduto);
            tvPrecoAtual = itemView.findViewById(R.id.tvPrecoAtual);
            tvMediaNota = itemView.findViewById(R.id.tvMediaNota);
            btnFavoritar = itemView.findViewById(R.id.btnFavoritar);
        }
    }
}
