package com.example.promohawk;

import com.example.promohawk.model.CadastroRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/registrar") // ajuste se o endpoint for diferente
    Call<Void> registrar(@Body CadastroRequest request);
}
