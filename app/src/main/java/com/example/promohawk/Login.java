package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.model.LoginRequest;
import com.example.promohawk.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnEntrar;
    private TextView btnCadastrar, btnEsqueceuSenha;
    private ImageView btnToggleSenha;
    private boolean senhaVisivel = false;

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
        btnToggleSenha = findViewById(R.id.btnToggleSenha);

        // Alternar visibilidade da senha
        ImageView btnToggleSenha = findViewById(R.id.btnToggleSenha);
        EditText editSenha = findViewById(R.id.editSenha);

// Flag para controlar o estado da senha
        final boolean[] isSenhaVisivel = {false};

        btnToggleSenha.setOnClickListener(v -> {
            if (isSenhaVisivel[0]) {
                // Ocultar senha
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnToggleSenha.setImageResource(R.drawable.olho_fechado);
            } else {
                // Mostrar senha
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                btnToggleSenha.setImageResource(R.drawable.olho_aberto);
            }
            // Mantém o cursor no final do texto
            editSenha.setSelection(editSenha.length());
            isSenhaVisivel[0] = !isSenhaVisivel[0];
        });



        btnEntrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor, insira o email.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (senha.isEmpty()) {
                Toast.makeText(this, "Por favor, insira a senha.", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest loginRequest = new LoginRequest(email, senha);
            ApiService apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);
            Call<LoginResponse> call = apiService.login(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getAccessToken();
                        getSharedPreferences("usuario_prefs", MODE_PRIVATE)
                                .edit()
                                .putString("token", token)
                                .apply();

                        Intent intent = new Intent(Login.this, Config.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Login.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
        });

        btnEsqueceuSenha.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, RecuperarSenha.class);
            startActivity(intent);
        });
    }
}
