package com.example.promohawk.api;

import com.example.promohawk.model.CadastroRequest;
import com.example.promohawk.Categoria;
import com.example.promohawk.model.Compra;
import com.example.promohawk.Cupom;
import com.example.promohawk.model.LoginRequest;
import com.example.promohawk.model.LoginResponse;
import com.example.promohawk.model.PerfilRequest;
import com.example.promohawk.Produto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    // ===============================
    // Autenticação e Usuário
    // ===============================

    @POST("users")
    Call<Void> registrar(@Body CadastroRequest request);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @PUT("perfil")
    Call<Void> atualizarPerfil(@Body PerfilRequest perfilRequest);

    // ===============================
    // Dados do Usuário
    // ===============================

    @GET("compras")
    Call<List<Compra>> getHistoricoCompras();

    // ===============================
    // Dados para Home
    // ===============================

    @GET("produtos")
    Call<List<Produto>> getProdutos();

    @GET("cupons")
    Call<List<Cupom>> getCupons();

    @GET("categorias")
    Call<List<Categoria>> getCategorias();
}
