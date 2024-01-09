package com.rabh.projectsegundoparcialapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Platillo;
import com.rabh.projectsegundoparcialapp.repository.PlatilloRepository;

import java.util.List;

public class PlatilloViewModel extends AndroidViewModel {
    private final PlatilloRepository repository;

    public PlatilloViewModel(@NonNull Application application) {
        super(application);
        repository = PlatilloRepository.getInstance();
    }
    public LiveData<GenericResponse<List<Platillo>>> listarPlatillosRecomendados(){
        return this.repository.listarPlatillosRecomendados();
    }
    public LiveData<GenericResponse<List<Platillo>>> listarPlatillosPorCategoria(int idC){
        return this.repository.listarPlatillosPorCategoria(idC);
    }
}
