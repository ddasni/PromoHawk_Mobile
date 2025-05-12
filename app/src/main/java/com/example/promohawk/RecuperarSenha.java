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

public class RecuperarSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_senha);

        // Aplicar padding para evitar sobreposição com a barra de status
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Aqui ficam suas ações normais
        EditText editEmailRecuperacao = findViewById(R.id.editEmailRecuperacao);
        Button btnEnviar = findViewById(R.id.btnEnviarCodigo);
        TextView btnVoltar1 = findViewById(R.id.btnVoltarLogin1);

        btnEnviar.setOnClickListener(v -> {
            String email = editEmailRecuperacao.getText().toString().trim();

            if (email.isEmpty() || !email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Insira um e-mail válido (@gmail.com)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Código de recuperação enviado para o e-mail", Toast.LENGTH_SHORT).show();
                // Aqui você pode implementar a lógica real de envio
            }
        });

        btnVoltar1.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }
}
