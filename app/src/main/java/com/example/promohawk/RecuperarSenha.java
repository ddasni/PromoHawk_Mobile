package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
import com.example.promohawk.model.EsqueceuSenhaRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarSenha extends AppCompatActivity {

    private EditText editEmailRecuperacao;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_senha);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editEmailRecuperacao = findViewById(R.id.editEmailRecuperacao);
        Button btnEnviar = findViewById(R.id.btnEnviarCodigo);
        TextView btnVoltar1 = findViewById(R.id.btnVoltarLogin1);

        // Inicializa o Retrofit corretamente com ApiService
        apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);

        btnEnviar.setOnClickListener(v -> {
            String email = editEmailRecuperacao.getText().toString().trim();

            if (email.isEmpty() || !email.contains("@")) {
                Toast.makeText(this, "Insira um e-mail válido", Toast.LENGTH_SHORT).show();
            } else {
                enviarCodigo(email);
            }
        });

        btnVoltar1.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }

    private void enviarCodigo(String email) {
        EsqueceuSenhaRequest request = new EsqueceuSenhaRequest(email);

        apiService.esqueceuSenha(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RecuperarSenha.this, "Código enviado! Verifique seu e-mail.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RecuperarSenha.this, "Erro ao enviar código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RecuperarSenha.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
