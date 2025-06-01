package com.example.promohawk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Suporte extends AppCompatActivity {

    private EditText edtNome, edtMensagem;
    private Button btnEnviar;
    private ImageView btnEmail, btnWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suporte);

        edtNome = findViewById(R.id.edtNome);
        edtMensagem = findViewById(R.id.edtMensagem);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnEmail = findViewById(R.id.btnEmail);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);

        btnEnviar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();
            String mensagem = edtMensagem.getText().toString().trim();

            if (nome.isEmpty() || mensagem.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Aqui você pode enviar a mensagem para o servidor futuramente
            Toast.makeText(this, "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show();
            edtNome.setText("");
            edtMensagem.setText("");
        });

        btnEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:suporte@promohawk.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Ajuda via App");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Nenhum app de email instalado.", Toast.LENGTH_SHORT).show();
            }
        });

        btnWhatsapp.setOnClickListener(v -> {
            String numero = "5511999999999"; // coloque o número real com DDI e DDD
            String url = "https://wa.me/" + numero;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "WhatsApp não encontrado.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
