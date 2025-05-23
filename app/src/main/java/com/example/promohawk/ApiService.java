package com.example.promohawk;

import com.example.promohawk.model.CadastroRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("users")
    Call<Void> registrar(@Body CadastroRequest request);
}

