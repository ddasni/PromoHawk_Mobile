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

public class LojaAdapter extends RecyclerView.Adapter<LojaAdapter.LojaViewHolder> {

    private final Context context;
    private final List<LojaCard> lojas;

    public LojaAdapter(Context context, List<LojaCard> lojas) {
        this.context = context;
        this.lojas = lojas;
    }

    @NonNull
    @Override
    public LojaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loja, parent, false);
        return new LojaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LojaViewHolder holder, int position) {
        LojaCard loja = lojas.get(position);
        holder.txtNome.setText(loja.getNome());
        holder.imgIcon.setImageResource(loja.getImagemRes());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, loja.getActivityClass());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lojas.size();
    }

    static class LojaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome;
        ImageView imgIcon;

        public LojaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeLoja);
            imgIcon = itemView.findViewById(R.id.imgIconLoja);
        }
    }
}
