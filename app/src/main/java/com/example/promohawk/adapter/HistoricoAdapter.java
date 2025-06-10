package com.example.promohawk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.promohawk.R;
import com.example.promohawk.model.Compra;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.CompraViewHolder> {

    private List<Compra> listaCompras;

    public HistoricoAdapter(List<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    @NonNull
    @Override
    public CompraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historicco , parent, false);
        return new CompraViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraViewHolder holder, int position) {
        Compra compra = listaCompras.get(position);
        holder.dataCompra.setText(compra.getData());
        holder.nomeProduto.setText(compra.getNomeProduto());
        holder.precoPago.setText("Pago: " + compra.getPreco());
        holder.lojaCompra.setText("Comprado em: " + compra.getLoja());

        // Se houver imagem via URL
        Glide.with(holder.itemView.getContext())
                .load(compra.getImagemUrl())
                .placeholder(R.drawable.produto) // imagem padrão
                .into(holder.imagemProduto);
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }

    public static class CompraViewHolder extends RecyclerView.ViewHolder {
        TextView dataCompra, nomeProduto, precoPago, lojaCompra;
        ImageView imagemProduto;
        Button botaoComprarNovamente;

        public CompraViewHolder(@NonNull View itemView) {
            super(itemView);
            dataCompra = itemView.findViewById(R.id.dataCompra);
            nomeProduto = itemView.findViewById(R.id.nomeProdutoHistorico);
            precoPago = itemView.findViewById(R.id.precoPago);
            lojaCompra = itemView.findViewById(R.id.lojaCompra);
            imagemProduto = itemView.findViewById(R.id.imagemProdutoHistorico);
            botaoComprarNovamente = itemView.findViewById(R.id.botaoComprarNovamente);
        }
    }
}
