package com.rabh.project.service.controller;

import com.rabh.project.service.entity.Usuario;
import com.rabh.project.service.service.UsuarioService;
import com.rabh.project.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {

        this.service = service;
    }

    @PostMapping("/login")
    public GenericResponse<Usuario> login(HttpServletRequest request){
        String email = request.getParameter("email");
        String contrasenia = request.getParameter("pass");
        return this.service.login(email, contrasenia);
    }

    @PostMapping
    public GenericResponse save(@RequestBody Usuario u){
        return this.service.guardarUsuario(u);
    }

    @PutMapping("/{id}")
    public GenericResponse update(@PathVariable int id, @RequestBody Usuario u){
        return this.service.guardarUsuario(u);
    }
}
