package com.example.promohawk;

import com.example.promohawk.model.CadastroRequest;
import com.example.promohawk.model.LoginRequest;
import com.example.promohawk.model.LoginResponse;
import com.example.promohawk.model.PerfilRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    // Registro de usuário
    @POST("users")
    Call<Void> registrar(@Body CadastroRequest request);

    // Login
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // Atualizar perfil
    @PUT("perfil")
    Call<Void> atualizarPerfil(@Body PerfilRequest perfilRequest);

    // ===============================
    // NOVOS MÉTODOS PARA HOME.JAVA
    // ===============================

    @GET("produtos")
    Call<List<Produto>> getProdutos();

    @GET("cupons")
    Call<List<Cupom>> getCupons();

    @GET("categorias")
    Call<List<Categoria>> getCategorias();
}
