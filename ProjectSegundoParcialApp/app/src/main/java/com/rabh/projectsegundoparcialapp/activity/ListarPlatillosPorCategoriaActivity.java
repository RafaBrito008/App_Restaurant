package com.rabh.projectsegundoparcialapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.rabh.projectsegundoparcialapp.R;
import com.rabh.projectsegundoparcialapp.adapter.PlatillosPorCategoriaAdapter;
import com.rabh.projectsegundoparcialapp.communication.MostrarBadgeCommunication;
import com.rabh.projectsegundoparcialapp.entity.service.DetallePedido;
import com.rabh.projectsegundoparcialapp.entity.service.Platillo;
import com.rabh.projectsegundoparcialapp.utils.Carrito;
import com.rabh.projectsegundoparcialapp.viewmodel.PlatilloViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListarPlatillosPorCategoriaActivity extends AppCompatActivity implements MostrarBadgeCommunication {
    private PlatilloViewModel platilloViewModel;
    private PlatillosPorCategoriaAdapter adapter;
    private List<Platillo> platillos = new ArrayList<>();
    private RecyclerView rcvPlatilloPorCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_platillos_por_categoria);

        init();
        initViewModel();
        initAdapter();
        loadData();

    }

    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.platilloViewModel = vmp.get(PlatilloViewModel.class);
    }

    private void initAdapter() {
        adapter = new PlatillosPorCategoriaAdapter(platillos, this);
        rcvPlatilloPorCategoria = findViewById(R.id.rcvPlatillosPorCategoria);
        rcvPlatilloPorCategoria.setAdapter(adapter);
        rcvPlatilloPorCategoria.setLayoutManager(new LinearLayoutManager(this));
        //rcvPlatilloPorCategoria.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadData() {
        int idC = getIntent().getIntExtra("idC", 0);//Recibimos el idCategoria
        platilloViewModel.listarPlatillosPorCategoria(idC).observe(this, response -> {
            adapter.updateItems(response.getBody());
        });
    }


    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarPlatillos(dp));
        BadgeDrawable badgeDrawable = BadgeDrawable.create(this);
        badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawable, findViewById(R.id.toolbar), R.id.bolsaCompras);
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Se agregó el platillo!")
                .setContentText(message).show();
    }
}