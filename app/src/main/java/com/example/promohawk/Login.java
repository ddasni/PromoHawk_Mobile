package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnEntrar;
    private TextView btnCadastrar, btnEsqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializando os componentes da UI
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnEsqueceuSenha = findViewById(R.id.btnEsqueceuSenha);

        // Evento de clique para o botão "Entrar"
        btnEntrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            // Verificando se os campos estão vazios
            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor, insira o email.", Toast.LENGTH_SHORT).show();
                return;  // Interrompe a execução aqui, se o email estiver vazio
            }

            if (senha.isEmpty()) {
                Toast.makeText(this, "Por favor, insira a senha.", Toast.LENGTH_SHORT).show();
                return;  // Interrompe a execução aqui, se a senha estiver vazia
            }

            // Verificando se o login é correto
            if (email.equals("usuario@email.com") && senha.equals("123456")) {
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento de clique para o botão "Cadastrar"
        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
        });

        // Evento de clique para o botão "Esqueceu Senha"
        btnEsqueceuSenha.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, RecuperarSenha.class);
            startActivity(intent);


        });
    }
}
