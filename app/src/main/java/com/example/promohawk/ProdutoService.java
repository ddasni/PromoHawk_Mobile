package com.example.promohawk;

import com.example.promohawk.model.ProdutoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProdutoService {
    @GET("produtos/{id}")
    Call<ProdutoResponse> getProduto(@Path("id") int id);
}
