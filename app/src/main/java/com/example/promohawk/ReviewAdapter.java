package com.example.promohawk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> listaReviews;

    public ReviewAdapter(List<Review> listaReviews) {
        this.listaReviews = listaReviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = listaReviews.get(position);
        holder.txtUsuario.setText(review.getNomeUsuario());
        holder.ratingBar.setRating(review.getNota());
        holder.txtComentario.setText(review.getComentario());
    }

    @Override
    public int getItemCount() {
        return listaReviews != null ? listaReviews.size() : 0;
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsuario, txtComentario;
        RatingBar ratingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsuario = itemView.findViewById(R.id.txtUsuario);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            ratingBar = itemView.findViewById(R.id.ratingBarReview);
        }
    }
}
