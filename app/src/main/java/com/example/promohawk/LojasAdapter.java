package com.example.promohawk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LojasAdapter extends RecyclerView.Adapter<LojasAdapter.LojaViewHolder> {
    private List<LojaCard> lojas;
    private Context context;

    public LojasAdapter(List<LojaCard> lojas, Context context) {
        this.lojas = lojas;
        this.context = context;
    }

    @NonNull
    @Override
    public LojaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loja, parent, false);
        return new LojaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LojaViewHolder holder, int position) {
        LojaCard loja = lojas.get(position);

        holder.nomeLoja.setText(loja.getNome());
        holder.imagemLoja.setImageResource(loja.getImagemResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, loja.getActivityDestino());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return lojas.size(); }

    public static class LojaViewHolder extends RecyclerView.ViewHolder {
        ImageView imagemLoja;
        TextView nomeLoja;

        public LojaViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemLoja = itemView.findViewById(R.id.imgIconLoja);
            nomeLoja = itemView.findViewById(R.id.txtNomeLoja);
        }
    }
}