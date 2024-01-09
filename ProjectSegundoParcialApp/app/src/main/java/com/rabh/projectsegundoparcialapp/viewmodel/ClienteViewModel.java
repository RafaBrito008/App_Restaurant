package com.rabh.projectsegundoparcialapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Cliente;
import com.rabh.projectsegundoparcialapp.repository.ClienteRepository;

public class ClienteViewModel extends AndroidViewModel {
    private final ClienteRepository repository;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente c){
        return  repository.guardarCliente(c);
    }
}
