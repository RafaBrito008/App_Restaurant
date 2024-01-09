package com.rabh.projectsegundoparcialapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabh.projectsegundoparcialapp.R;
import com.rabh.projectsegundoparcialapp.activity.ui.compras.DetalleMisComprasActivity;
import com.rabh.projectsegundoparcialapp.communication.AnularPedidoCommunication;
import com.rabh.projectsegundoparcialapp.communication.Communication;
import com.rabh.projectsegundoparcialapp.entity.service.dto.PedidoConDetallesDTO;
import com.rabh.projectsegundoparcialapp.utils.DateSerializer;
import com.rabh.projectsegundoparcialapp.utils.TimeSerializer;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MisComprasAdapter extends RecyclerView.Adapter<MisComprasAdapter.ViewHolder> {
    private final List<PedidoConDetallesDTO> pedidos;
    private final Communication communication;
    private final AnularPedidoCommunication anularPedidoCommunication;

    public MisComprasAdapter(List<PedidoConDetallesDTO> pedidos, Communication communication, AnularPedidoCommunication anularPedidoCommunication) {
        this.pedidos = pedidos;
        this.communication = communication;
        this.anularPedidoCommunication = anularPedidoCommunication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mis_compras, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.pedidos.get(position));
    }

    @Override
    public int getItemCount() {

        return this.pedidos.size();
    }

    public void updateItems(List<PedidoConDetallesDTO> pedido){
        this.pedidos.clear();
        this.pedidos.addAll(pedido);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final PedidoConDetallesDTO dto) {
            final TextView txtValueCodPurchases = this.itemView.findViewById(R.id.txtValueCodPurchases),
                    txtValueDatePurchases = this.itemView.findViewById(R.id.txtValueDatePurchases),
                    txtValueAmount = this.itemView.findViewById(R.id.txtValueAmount),
                    txtValueOrder = this.itemView.findViewById(R.id.txtValueOrder);

            txtValueCodPurchases.setText("P00" + Integer.toString(dto.getPedido().getId()));
            txtValueDatePurchases.setText((dto.getPedido().getFechaCompra()).toString());
            txtValueAmount.setText(String.format(Locale.ENGLISH, "$%.2f", dto.getPedido().getMonto()));
            txtValueOrder.setText(dto.getPedido().isAnularPedido() ? "Pedido cancelado" : "Despachado, en proceso de envio...");


            itemView.setOnClickListener(v -> {
                final Intent i = new Intent(itemView.getContext(), DetalleMisComprasActivity.class);
                final Gson g = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .registerTypeAdapter(Time.class, new TimeSerializer())
                        .create();
                i.putExtra("detailsPurchases", g.toJson(dto.getDetallePedido()));
                communication.showDetails(i);//Animación para mostrar el detalle
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    anularPedido(dto.getPedido().getId());
                    return true;
                }
            });

        }

        private void anularPedido(int id) {
            new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("¡Adevertencia!")
                    .setContentText("¿Estás seguro de cancelar el pedido?")
                    .setCancelText("No").setConfirmText("Sí")
                    .showCancelButton(true)
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Se canceló el pedido")
                                .setContentText(anularPedidoCommunication.anularPedido(id))
                                .show();
                    }).setCancelClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("¡Operación Cancelada!")
                                .setContentText("No cancelaste ningún pedido")
                                .show();
                    }).show();
        }
    }
}
