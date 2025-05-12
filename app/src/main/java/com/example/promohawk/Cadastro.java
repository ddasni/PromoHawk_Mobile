package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {

    private EditText editNomeUsuario, editEmail, editData, editTelefone, editSenha, editConfirmarSenha;
    private Button btnCadastrar;
    private TextView txtLogin, txtNs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNomeUsuario = findViewById(R.id.EditNusuario);
        editEmail = findViewById(R.id.EditEmail1);
        editData = findViewById(R.id.Editdata);
        editTelefone = findViewById(R.id.EditTelefone);
        editSenha = findViewById(R.id.EditSenhaC);
        editConfirmarSenha = findViewById(R.id.EditSenhaC1);
        btnCadastrar = findViewById(R.id.btnEntrar1);
        txtLogin = findViewById(R.id.txtLogin1);
        txtNs = findViewById(R.id.txtNs); // ignorado

        // Aplica limites de caracteres
        editNomeUsuario.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        editEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        editData.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        editTelefone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editSenha.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        editConfirmarSenha.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        btnCadastrar.setOnClickListener(v -> {
            String nome = editNomeUsuario.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String data = editData.getText().toString().trim();
            String telefone = editTelefone.getText().toString().trim();
            String senha = editSenha.getText().toString();
            String confirmarSenha = editConfirmarSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || data.isEmpty() || telefone.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.endsWith("@gmail.com")) {
                Toast.makeText(this, "O e-mail deve ser @gmail.com", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Se tudo estiver certo, só então vai para a tela de verificação
            Intent intent = new Intent(Cadastro.this, Home.class);
            startActivity(intent);
        });

        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
