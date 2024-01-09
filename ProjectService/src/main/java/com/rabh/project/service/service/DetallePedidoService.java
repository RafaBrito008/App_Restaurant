package com.rabh.project.service.service;

import com.rabh.project.service.entity.DetallePedido;
import com.rabh.project.service.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DetallePedidoService {
    private final DetallePedidoRepository repository;

    public DetallePedidoService(DetallePedidoRepository repository) {
        this.repository = repository;
    }
    //Método para guardar detalles del pedido
    public void guardarDetalles(Iterable<DetallePedido> detalle){
        this.repository.saveAll(detalle);
    }
}