package com.rabh.project.service.controller;

import com.rabh.project.service.entity.dto.GenerarPedidoDTO;
import com.rabh.project.service.entity.dto.PedidoConDetallesDTO;
import com.rabh.project.service.service.PedidoService;
import com.rabh.project.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }
    //LISTAR PEDIDOS CON DETALLES
    @GetMapping("/misPedidos/{idCli}")
    public GenericResponse<List<PedidoConDetallesDTO>> devolverMisComprasConDetalle(@PathVariable int idCli){
        return this.service.devolverMisCompras(idCli);
    }

    //GUARDAR PEDIDO
    @PostMapping
    public GenericResponse guardarPedido(@RequestBody GenerarPedidoDTO dto){
        return this.service.guardarPedido(dto);
    }

    //ANULAR PEDIDO
    @DeleteMapping("/{id}")
    public GenericResponse anularPedido(@PathVariable int id){
        return this.service.anularPedido(id);
    }
}