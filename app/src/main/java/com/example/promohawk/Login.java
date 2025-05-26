package com.example.promohawk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promohawk.model.LoginRequest;
import com.example.promohawk.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            // Cria a requisição
            LoginRequest loginRequest = new LoginRequest(email, senha);

            // Chama a API
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
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
