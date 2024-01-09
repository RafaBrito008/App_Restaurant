package com.rabh.projectsegundoparcialapp.api;

import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base = "api/categoria";

    @GET(base)
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
