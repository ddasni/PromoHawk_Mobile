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


public class CupomAdapter extends RecyclerView.Adapter<CupomAdapter.CupomViewHolder> {
    private Context context;
    private List<Cupom> listaCupons;

    public CupomAdapter(Context context, List<Cupom> listaCupons) {
        this.context = context;
        this.listaCupons = listaCupons;
    }

    @NonNull
    @Override
    public CupomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cupom_item, parent, false);
        return new CupomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CupomViewHolder holder, int position) {
        Cupom cupom = listaCupons.get(position);
        holder.cupomDesconto.setText(cupom.getDesconto());
        holder.cupomDescricao.setText(cupom.getDescricao());
        holder.cupomCodigo.setText(cupom.getCodigo());

        // Se estiver usando Glide para carregar imagens
        Glide.with(context)
                .load(cupom.getImagemUrl())
                .into(holder.cupomImagem);
    }

    @Override
    public int getItemCount() {
        return listaCupons.size();
    }

    public class CupomViewHolder extends RecyclerView.ViewHolder {
        ImageView cupomImagem;
        TextView cupomDesconto, cupomDescricao, cupomCodigo;

        public CupomViewHolder(@NonNull View itemView) {
            super(itemView);
            cupomImagem = itemView.findViewById(R.id.cupomImagem);
            cupomDesconto = itemView.findViewById(R.id.cupomDesconto);
            cupomDescricao = itemView.findViewById(R.id.cupomDescricao);
            cupomCodigo = itemView.findViewById(R.id.cupomCodigo);
        }
    }
}

