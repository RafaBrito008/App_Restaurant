package com.rabh.project.service.service;

import com.rabh.project.service.entity.Usuario;
import com.rabh.project.service.repository.UsuarioRepository;
import com.rabh.project.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.rabh.project.service.utils.Global.*;

@Service@Transactional
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    //MÉTODO PARA INICIAR SESIÓN
    public GenericResponse<Usuario> login(String email, String contrasenia){
        Optional<Usuario> optU = this.repository.login(email, contrasenia);

        if(optU.isPresent()){
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_OK, "Iniciaste sesión correctamente", optU.get());
        }else{
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_WARNING, "El usuario ingresado no existe", new Usuario());
        }
    }

    //MÉTODO PARA GUARDAR CREDENCIALES DEL USUARIO
    public GenericResponse guardarUsuario(Usuario u){
        Optional<Usuario> optU = this.repository.findById(u.getId());
        int idf = optU.isPresent() ? optU.get().getId() : 0;
        if(idf == 0){
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Usuario Registrado Correctamente", this.repository.save(u));
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Datos del usuario actualizados", this.repository.save(u));
        }
    }
}
