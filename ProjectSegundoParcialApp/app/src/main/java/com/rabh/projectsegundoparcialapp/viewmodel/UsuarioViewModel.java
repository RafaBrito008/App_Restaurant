package com.rabh.projectsegundoparcialapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Usuario;
import com.rabh.projectsegundoparcialapp.repository.UsuarioRepository;

import org.jetbrains.annotations.NotNull;

public class UsuarioViewModel extends AndroidViewModel {
    private final UsuarioRepository repository;

    public UsuarioViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();
    }

    public LiveData<GenericResponse<Usuario>> login(String email, String pass){
        return this.repository.login(email, pass);
    }

    public LiveData<GenericResponse<Usuario>> save(Usuario u){
        return this.repository.save(u);
    }
}
