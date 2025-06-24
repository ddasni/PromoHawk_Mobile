// === ApiService.java (completo e atualizado) ===
package com.example.promohawk.api;

import com.example.promohawk.model.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    Call<ProdutoListResponse> getProduto();

    @GET("produto/{id}")
    Call<Produto> getProduto(@Path("id") int id);


    // ==== Cupons ====
    @GET("cupom")
    Call<CupomListResponse> getCupom();

    @GET("cupom/{id}")
    Call<CupomResponse> getCupom(@Path("id") int id); // ✅ Novo método


    // ==== Categorias ====
    @GET("categoria")
    Call<CategoriaListResponse> getCategoria();

    @GET("categoria/{id}")
    Call<CategoriaResponse> getCategoria(@Path("id") int id); // ✅ Novo método


    // ==== Histórico ====
    @GET("compra")
    Call<List<Compra>> getHistoricoCompras();
}
