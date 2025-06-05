package com.example.promohawk;

import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImagemAdapter extends RecyclerView.Adapter<ImagemAdapter.ViewHolder> {

    private List<String> imagens; // Agora são URLs

    public ImagemAdapter(List<String> imagens) {
        this.imagens = imagens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        Glide.with(holder.imageView.getContext())
                .load(imagens.get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(@NonNull ImageView view) {
            super(view);
            imageView = view;
        }
    }
}
