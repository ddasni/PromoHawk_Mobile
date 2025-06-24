package com.example.promohawk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.promohawk.model.Cupom;
import java.util.List;

public class CupomAdapter extends RecyclerView.Adapter<CupomAdapter.CupomViewHolder> {

    private final Context context;
    private final List<Cupom> listaCupons;

    public CupomAdapter(Context context, List<Cupom> listaCupons) {
        this.context = context;
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

        holder.tvVerCodigo.setOnClickListener(v -> {
            holder.layoutCodigo.setVisibility(View.VISIBLE);
            holder.tvVerCodigo.setVisibility(View.GONE);
            holder.tvCodigo.setText(cupom.getCodigo());
        });

        holder.btnCopiar.setOnClickListener(v -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Cupom", cupom.getCodigo());
            clipboard.setPrimaryClip(clip);
            android.widget.Toast.makeText(context, "Código copiado!", android.widget.Toast.LENGTH_SHORT).show();
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
