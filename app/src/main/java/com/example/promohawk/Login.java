package com.example.promohawk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.promohawk.api.ApiService;
import com.example.promohawk.api.RetrofitClient;
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
    private CheckBox checkBox;  // Checkbox para "Permanecer conectado"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBox = findViewById(R.id.checkBox);

        // Carregar estado da checkbox e verificar token
        SharedPreferences prefs = getSharedPreferences("usuario_prefs", MODE_PRIVATE);
        boolean manterConectado = prefs.getBoolean("manterConectado", false);
        String token = prefs.getString("token", null);

        checkBox.setChecked(manterConectado);

        if (manterConectado && token != null && !token.isEmpty()) {
            // Usuário quer manter conectado e tem token salvo, vai direto para a tela principal
            Intent intent = new Intent(Login.this, Config.class);
            startActivity(intent);
            finish();
            return; // Evita continuar carregando tela de login
        }

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnEsqueceuSenha = findViewById(R.id.btnEsqueceuSenha);
        btnToggleSenha = findViewById(R.id.btnToggleSenha);

        final boolean[] isSenhaVisivel = {false};
        btnToggleSenha.setOnClickListener(v -> {
            if (isSenhaVisivel[0]) {
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnToggleSenha.setImageResource(R.drawable.olho_fechado);
            } else {
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                btnToggleSenha.setImageResource(R.drawable.olho_aberto);
            }
            editSenha.setSelection(editSenha.length());
            isSenhaVisivel[0] = !isSenhaVisivel[0];
        });

        btnEntrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest loginRequest = new LoginRequest(email, senha);
            ApiService apiService = RetrofitClient.getPromoHawkInstance().create(ApiService.class);
            Call<LoginResponse> call = apiService.login(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse login = response.body();

                        SharedPreferences prefs = getSharedPreferences("usuario_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        boolean manterConectado = checkBox.isChecked();

                        if (manterConectado) {
                            editor.putString("token", "Bearer "+ login.getAccessToken());
                            editor.putString("email", login.getEmail());
                            editor.putString("nome", login.getNome());

                            String chaveFoto = "fotoPerfil_" + login.getEmail();
                            editor.putString(chaveFoto, login.getFotoUrl());
                        } else {
                            editor.clear(); // Limpa os dados caso não queira manter conectado
                        }

                        editor.putBoolean("manterConectado", manterConectado);
                        editor.apply();

                        Log.d("TOKEN_SALVO", "Token: " + login.getAccessToken());

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

        btnCadastrar.setOnClickListener(v -> startActivity(new Intent(this, Cadastro.class)));
        btnEsqueceuSenha.setOnClickListener(v -> startActivity(new Intent(this, RecuperarSenha.class)));
    }
}
