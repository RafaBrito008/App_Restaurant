package com.rabh.projectsegundoparcialapp.api;

import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Pedido;
import com.rabh.projectsegundoparcialapp.entity.service.dto.GenerarPedidoDTO;
import com.rabh.projectsegundoparcialapp.entity.service.dto.PedidoConDetallesDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface PedidoApi {
    String base = "api/pedido";

    @GET(base + "/misPedidos/{idCli}")
    Call<GenericResponse<List<PedidoConDetallesDTO>>> listarPedidosPorCliente(@Path("idCli") int idCli);

    @POST(base)
    Call<GenericResponse<GenerarPedidoDTO>> guardarPedido(@Body GenerarPedidoDTO dto);

    @DELETE(base + "/{id}")
    Call<GenericResponse<Pedido>> anularPedido(@Path("id") int id);
}
