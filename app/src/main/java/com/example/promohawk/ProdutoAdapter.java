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
import com.example.promohawk.ProdutoDetalheActivity;
import com.example.promohawk.R;
import com.example.promohawk.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private Context context;
    private List<Produto> produtos;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        holder.preco.setText(produto.getPreco());

        Glide.with(context)
                .load(produto.getImagemUrl()) // imagem vinda da API
                .placeholder(R.drawable.placeholder) // opcional
                .into(holder.imagem);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProdutoDetalheActivity.class);
            intent.putExtra("produto", produto); // certifique-se que Produto é Serializable ou Parcelable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        TextView nome, preco;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imgProduto);
            nome = itemView.findViewById(R.id.textNomeProduto);
            preco = itemView.findViewById(R.id.textPrecoProduto);
        }
    }
}
