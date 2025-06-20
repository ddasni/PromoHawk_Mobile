package com.example.promohawk;

import com.example.promohawk.model.Produto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProdutoService {
    @GET("produtos/{id}")
    Call<Produto> getProduto(@Path("id") String id);
}
