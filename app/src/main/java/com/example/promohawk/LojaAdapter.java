package com.example.promohawk;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LojaAdapter extends RecyclerView.Adapter<LojaAdapter.LojaViewHolder> {

    private List<Lojas.Loja> lojas;

    public LojaAdapter(List<Lojas.Loja> lojas) {
        this.lojas = lojas;
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
        Lojas.Loja loja = lojas.get(position);
        holder.txtNome.setText(loja.nome);
        holder.imgIcon.setImageResource(loja.imagemRes);
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
