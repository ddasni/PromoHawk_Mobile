package com.example.promohawk;

import com.example.promohawk.model.CadastroRequest;
import com.example.promohawk.model.LoginRequest;
import com.example.promohawk.model.LoginResponse;
import com.example.promohawk.model.PerfilRequest;
import retrofit2.http.PUT;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("users")
    Call<Void> registrar(@Body CadastroRequest request);

    @POST("auth/login") // ou o endpoint correto da sua API
    Call<LoginResponse> login(@Body LoginRequest request);

    @PUT("perfil") // ou o endpoint correto da sua API para atualizar perfil
    Call<Void> atualizarPerfil(@Body PerfilRequest perfilRequest);

}

