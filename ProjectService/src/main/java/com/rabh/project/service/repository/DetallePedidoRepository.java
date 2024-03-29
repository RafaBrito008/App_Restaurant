package com.rabh.project.service.repository;

import com.rabh.project.service.entity.DetallePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DetallePedidoRepository extends CrudRepository<DetallePedido, Integer> {
    @Query("SELECT DP FROM DetallePedido DP WHERE DP.pedido.id=:idP")
    Iterable<DetallePedido> findByPedido(int idP);

}