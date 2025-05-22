package com.example.promohawk;
import com.example.promohawk.model.CadastroRequest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;


public class Cadastro extends AppCompatActivity {

    private EditText editNomeUsuario, editEmail, editData, editTelefone, editSenha, editConfirmarSenha;
    private Button btnCadastrar;
    private TextView txtletras, txtLogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        txtletras = findViewById(R.id.txtletras);
        txtLogin = findViewById(R.id.txtLogin);

// Adicione isto:
        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, Login.class);
            startActivity(intent);
            finish();
        });


        editNomeUsuario = findViewById(R.id.EditNusuario);
        editEmail = findViewById(R.id.EditEmail1);
        editData = findViewById(R.id.Editdata);
        editTelefone = findViewById(R.id.EditTelefone);
        editSenha = findViewById(R.id.EditSenhaC);
        editConfirmarSenha = findViewById(R.id.EditSenhaC1);
        btnCadastrar = findViewById(R.id.btnEntrar1);
        txtletras = findViewById(R.id.txtletras);
        txtLogin = findViewById(R.id.txtLogin);

        // Aplica limites de caracteres
        editNomeUsuario.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        editEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        editData.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        editTelefone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editSenha.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        editConfirmarSenha.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        // TextWatcher para formatar data no formato DD/MM/YYYY automaticamente
        editData.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "";
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String clean = s.toString().replaceAll("[^\\d]", "");

                StringBuilder formatted = new StringBuilder();

                int length = clean.length();

                if (length > 0) {
                    // Dia
                    String day = clean.substring(0, Math.min(2, length));
                    int dayInt = Integer.parseInt(day);
                    if (dayInt > 31) day = "31";
                    formatted.append(day);
                    if (day.length() == 2 && length > 2) formatted.append("/");
                }

                if (length > 2) {
                    // Mês
                    String month = clean.substring(2, Math.min(4, length));
                    int monthInt = Integer.parseInt(month);
                    if (monthInt > 12) month = "12";
                    formatted.append(month);
                    if (month.length() == 2 && length > 4) formatted.append("/");
                }

                if (length > 4) {
                    // Ano
                    String year = clean.substring(4, Math.min(8, length));
                    formatted.append(year);
                }

                isUpdating = true;
                editData.setText(formatted.toString());
                editData.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        btnCadastrar.setOnClickListener(v -> {
            String nome = editNomeUsuario.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String data = editData.getText().toString().trim(); // não usada na API ainda
            String telefone = editTelefone.getText().toString().trim();
            String senha = editSenha.getText().toString();
            String confirmarSenha = editConfirmarSenha.getText().toString();


                if (nome.isEmpty() || email.isEmpty() || data.isEmpty() || telefone.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.endsWith("@")) {
                Toast.makeText(this, "O e-mail deve ser @", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cria objeto do usuário
            CadastroRequest cadastroRequest = new CadastroRequest(nome, email, senha, telefone, data, confirmarSenha); // ✅ Certo


            // Prepara o Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.promohawk.com.br/api/") // 🔁 Coloque aqui a URL real
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);

            // Envia a requisição
            Call<Void> call = apiService.registrar(cadastroRequest);
            call.enqueue(new Callback<Void>()
            {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Cadastro.this, Config.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            if (response.errorBody() != null) {
                                String errorBody = response.errorBody().string();
                                Toast.makeText(Cadastro.this, "Erro: " + response.code() + "\n" + errorBody, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Cadastro.this, "Erro desconhecido: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(Cadastro.this, "Erro no servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }




                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(Cadastro.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


    }
}