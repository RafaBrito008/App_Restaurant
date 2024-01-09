package com.rabh.projectsegundoparcialapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rabh.projectsegundoparcialapp.api.ClienteApi;
import com.rabh.projectsegundoparcialapp.api.ConfigApi;
import com.rabh.projectsegundoparcialapp.entity.GenericResponse;
import com.rabh.projectsegundoparcialapp.entity.service.Cliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {
    private static ClienteRepository repository;
    private final ClienteApi api;

    public static ClienteRepository getInstance(){
        if(repository == null){
            repository = new ClienteRepository();
        }
        return repository;
    }
    private ClienteRepository(){
        api = ConfigApi.getClienteApi();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente c){
        final MutableLiveData<GenericResponse<Cliente>> mld = new MutableLiveData<>();
        this.api.guardarCliente(c).enqueue(new Callback<GenericResponse<Cliente>>() {
            @Override
            public void onResponse(Call<GenericResponse<Cliente>> call, Response<GenericResponse<Cliente>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Cliente>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error : " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
