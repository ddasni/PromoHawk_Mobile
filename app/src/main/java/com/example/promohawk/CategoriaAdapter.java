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
import com.example.promohawk.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private final Context context;
    private final List<Categoria> listaCategorias;
    private final CategoriaClickListener listener;

    public interface CategoriaClickListener {
        void onCategoriaClick(Categoria categoria);
    }

    public CategoriaAdapter(Context context, List<Categoria> listaCategorias, CategoriaClickListener listener) {
        this.context = context;
        this.listaCategorias = listaCategorias.size() > 6 ? listaCategorias.subList(0, 6) : listaCategorias;
        this.listener = listener;
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
                .load(categoria.getImagemUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imgCategoria);

        holder.itemView.setOnClickListener(v -> listener.onCategoriaClick(categoria));
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
