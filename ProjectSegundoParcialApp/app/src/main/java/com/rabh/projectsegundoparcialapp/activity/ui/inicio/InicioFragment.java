package com.rabh.projectsegundoparcialapp.activity.ui.inicio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.rabh.projectsegundoparcialapp.R;
import com.rabh.projectsegundoparcialapp.adapter.CategoriaAdapter;
import com.rabh.projectsegundoparcialapp.adapter.PlatillosRecomendadosAdapter;
import com.rabh.projectsegundoparcialapp.adapter.SliderAdapter;
import com.rabh.projectsegundoparcialapp.communication.Communication;
import com.rabh.projectsegundoparcialapp.communication.MostrarBadgeCommunication;
import com.rabh.projectsegundoparcialapp.entity.SliderItem;
import com.rabh.projectsegundoparcialapp.entity.service.DetallePedido;
import com.rabh.projectsegundoparcialapp.entity.service.Platillo;
import com.rabh.projectsegundoparcialapp.utils.Carrito;
import com.rabh.projectsegundoparcialapp.viewmodel.CategoriaViewModel;
import com.rabh.projectsegundoparcialapp.viewmodel.PlatilloViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InicioFragment extends Fragment implements Communication, MostrarBadgeCommunication {

    private CategoriaViewModel categoriaViewModel;
    private PlatilloViewModel platilloViewModel;
    private RecyclerView rcvPlatillosRecomendados;
    private PlatillosRecomendadosAdapter adapter;
    private List<Platillo> platillos = new ArrayList<>();
    private GridView gvCategorias;
    private CategoriaAdapter categoriaAdapter;
    private SliderView svCarrusel;
    private SliderAdapter sliderAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }

    private void init(View v){
        svCarrusel = v.findViewById(R.id.svCarrusel);
        ViewModelProvider vmp = new ViewModelProvider(this);
        //CATEGORIAS
        categoriaViewModel = vmp.get(CategoriaViewModel.class);
        gvCategorias = v.findViewById(R.id.gvCategorias);
        //PLATILLOS
        rcvPlatillosRecomendados = v.findViewById(R.id.rcvPlatillosRecomendados);
        rcvPlatillosRecomendados.setLayoutManager(new GridLayoutManager(getContext(), 1));
        platilloViewModel = vmp.get(PlatilloViewModel.class);
    }

    private void initAdapter() {
        //CARRUSEL DE IMÁGENES
        sliderAdapter = new SliderAdapter(getContext());
        svCarrusel.setSliderAdapter(sliderAdapter);
        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarrusel.setIndicatorSelectedColor(Color.WHITE);
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY);
        svCarrusel.setScrollTimeInSec(4); //set scroll delay in seconds :
        svCarrusel.startAutoCycle();

        //CATEGOÍIAS
        categoriaAdapter = new CategoriaAdapter(getContext(), R.layout.item_categorias, new ArrayList<>());
        gvCategorias.setAdapter(categoriaAdapter);

        //PLATILLOS
        adapter = new PlatillosRecomendadosAdapter(platillos, this, this);
        rcvPlatillosRecomendados.setAdapter(adapter);
    }

    private void loadData() {
        List<SliderItem> lista = new ArrayList<>();
        lista.add(new SliderItem(R.drawable.hamburguesas_inicio, "Hamburguesas"));
        lista.add(new SliderItem(R.drawable.alitas_inicio, "Alitas"));
        lista.add(new SliderItem(R.drawable.abrebocas_inicio, "Abrebocas"));
        lista.add(new SliderItem(R.drawable.bebidas_inicio, "Bebidas"));
        lista.add(new SliderItem(R.drawable.cocteles_inicio, "Cocteles"));

        sliderAdapter.updateItem(lista);

        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(), response -> {
            if(response.getRpta() == 1){
                categoriaAdapter.clear();
                categoriaAdapter.addAll(response.getBody());
                categoriaAdapter.notifyDataSetChanged();
            }else{
                System.out.println("Error al obtener las categorías activas");
            }
        });

        platilloViewModel.listarPlatillosRecomendados().observe(getViewLifecycleOwner(), response -> {
            adapter.updateItems(response.getBody());
        });

    }

    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarPlatillos(dp));
        BadgeDrawable badgeDrawable = BadgeDrawable.create(this.getContext());
        badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawable, getActivity().findViewById(R.id.toolbar), R.id.bolsaCompras);
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this.getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Se agregó el platillo!")
                .setContentText(message).show();
    }
}