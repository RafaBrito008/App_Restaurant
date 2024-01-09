package com.rabh.projectsegundoparcialapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Pedido;
import com.rabh.projectsegundoparcialapp.entity.service.dto.GenerarPedidoDTO;
import com.rabh.projectsegundoparcialapp.entity.service.dto.PedidoConDetallesDTO;
import com.rabh.projectsegundoparcialapp.repository.PedidoRepository;

import java.util.List;

public class PedidoViewModel extends AndroidViewModel {
    private final PedidoRepository repository;

    public PedidoViewModel(@NonNull Application application) {
        super(application);
        this.repository = PedidoRepository.getInstance();
    }

    public LiveData<GenericResponse<List<PedidoConDetallesDTO>>> listarPedidosPorCliente(int idCli){
        return this.repository.listarPedidosPorCliente(idCli);
    }

    public LiveData<GenericResponse<GenerarPedidoDTO>> guardarPedido(GenerarPedidoDTO dto){
        return repository.save(dto);
    }

    public LiveData<GenericResponse<Pedido>> anularPedido(int id){
        return repository.anularPedido(id);
    }

}
