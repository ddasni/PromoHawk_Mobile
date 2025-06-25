package com.example.promohawk.api;

import com.example.promohawk.Produto_Favorito;
import com.example.promohawk.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // ==== Autenticação e Perfil ====
    @POST("users")
    Call<Void> registrar(@Body CadastroRequest request);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @PUT("perfil")
    Call<Void> atualizarPerfil(@Body PerfilRequest perfilRequest);

    @PUT("perfil/foto")
    Call<Void> atualizarFotoPerfil(@Body FotoPerfilRequest fotoPerfilRequest);

    @PUT("perfil/foto-base64")
    Call<Void> atualizarFotoPerfilBase64(@Body FotoPerfilBase64Request request);


    // ==== Produtos ====
    @GET("produto")
    Call<ProdutoListResponse> listarProdutos(); // Renomeado para evitar duplicação

    @GET("produto/{id}")
    Call<Produto> buscarProdutoPorId(@Path("id") int id);


    // ==== Cupons ====
    @GET("cupom")
    Call<CupomListResponse> listarCupons();

    @GET("cupom/{id}")
    Call<CupomResponse> buscarCupomPorId(@Path("id") int id);


    // ==== Categorias ====
    @GET("categoria")
    Call<CategoriaListResponse> listarCategorias();

    @GET("categoria/{id}")
    Call<CategoriaResponse> buscarCategoriaPorId(@Path("id") int id);


    // ==== Histórico ====
    @GET("compra")
    Call<List<Compra>> getHistoricoCompras();
        @GET("user-favoritos")
        Call<List<Produto_Favorito>> getFavoritos(@Header("Authorization") String token);

}
