package com.rabh.project.service.service;

import com.rabh.project.service.repository.CategoriaRepository;
import com.rabh.project.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.rabh.project.service.utils.Global.*;

@Service
@Transactional
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }
    public GenericResponse listarCategoriasActivas(){
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, this.repository.listarCategoriasActivas());
    }
}