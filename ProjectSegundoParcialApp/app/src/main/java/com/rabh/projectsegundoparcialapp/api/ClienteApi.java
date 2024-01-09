package com.rabh.projectsegundoparcialapp.api;

import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {
    String base = "api/cliente";
    @POST(base)
    Call<GenericResponse<Cliente>> guardarCliente(@Body Cliente c);
}
