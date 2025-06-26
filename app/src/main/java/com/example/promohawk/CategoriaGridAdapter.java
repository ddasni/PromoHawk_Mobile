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

import com.bumptech.glide.Glide;
import com.example.promohawk.model.Categoria;

import java.util.List;

public class CategoriaGridAdapter extends RecyclerView.Adapter<CategoriaGridAdapter.CategoriaViewHolder> {

    private final Context context;
    private final List<Categoria> listaCategorias;

    public CategoriaGridAdapter(Context context, List<Categoria> listaCategorias) {
        this.context = context;
        this.listaCategorias = listaCategorias;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);
        holder.nomeCategoria.setText(categoria.getNome());

        Glide.with(context)
                .load(categoria.getImagem())
                .placeholder(R.drawable.placeholder)
                .into(holder.imgCategoria);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProdutosPorCategoriaActivity.class);
            intent.putExtra("categoria", categoria.getNome());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategoria;
        TextView nomeCategoria;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
            nomeCategoria = itemView.findViewById(R.id.nomeCategoria);
        }
    }
}
