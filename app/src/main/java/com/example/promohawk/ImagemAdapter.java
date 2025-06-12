package com.example.promohawk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImagemAdapter extends RecyclerView.Adapter<ImagemAdapter.ViewHolder> {

    private List<String> imagens;

    public ImagemAdapter(List<String> imagens) {
        this.imagens = imagens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla um layout XML personalizado para imagens, se necessário
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String urlImagem = imagens.get(position);
        Glide.with(holder.imageView.getContext())
                .load(urlImagem)
                .placeholder(R.drawable.placeholder) // opcional: imagem enquanto carrega
                .error(R.drawable.placeholder)             // opcional: imagem em caso de erro
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagens != null ? imagens.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView;
        }
    }
}
