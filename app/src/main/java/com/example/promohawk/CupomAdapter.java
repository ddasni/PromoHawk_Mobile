package com.example.promohawk;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.promohawk.model.Cupom;

import java.util.List;

public class CupomAdapter extends RecyclerView.Adapter<CupomAdapter.CupomViewHolder> {

    private final Context context;
    private final List<Cupom> listaCupons;

    public CupomAdapter(Context context, List<Cupom> listaCupons) {
        this.context = context;
        // Exibe no máximo 6 cupons
        this.listaCupons = listaCupons.size() > 6 ? listaCupons.subList(0, 6) : listaCupons;
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

        holder.tvData.setText(cupom.getData());
        holder.tvDesconto.setText(cupom.getDesconto());
        holder.tvDescricao.setText(cupom.getDescricao());
        holder.tvCodigo.setText(cupom.getCodigo()); // Garante que sempre exista o texto no campo

        // Resetar visibilidades para evitar reuso de view com estado errado
        holder.layoutCodigo.setVisibility(View.GONE);
        holder.tvVerCodigo.setVisibility(View.VISIBLE);

        // Ver código
        holder.tvVerCodigo.setOnClickListener(v -> {
            holder.layoutCodigo.setVisibility(View.VISIBLE);
            holder.tvVerCodigo.setVisibility(View.GONE);
        });

        // Botão copiar
        holder.btnCopiar.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Cupom", cupom.getCodigo());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Código copiado!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listaCupons.size();
    }

    public static class CupomViewHolder extends RecyclerView.ViewHolder {
        TextView tvData, tvDesconto, tvDescricao, tvVerCodigo, tvCodigo;
        LinearLayout layoutCodigo;
        Button btnCopiar;

        public CupomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvData);
            tvDesconto = itemView.findViewById(R.id.tvDesconto);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvVerCodigo = itemView.findViewById(R.id.tvVerCodigo);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            layoutCodigo = itemView.findViewById(R.id.layoutCodigo);
            btnCopiar = itemView.findViewById(R.id.btnCopiar);
        }
    }
}
