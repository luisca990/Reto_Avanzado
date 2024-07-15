package com.example.panappetit.Presentation.Dash.ManageProduct.History.Adapter;

import static com.example.panappetit.Utils.Util.convertImageService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panappetit.Models.Venta;
import com.example.panappetit.R;

import java.util.List;

public class RecyclerAdapterHistorial extends RecyclerView.Adapter<RecyclerAdapterHistorial.ViewHolder> {

    private List<Venta> ventas;
    private final Context context;
    private final OnItemClickListenerHistory listener;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Venta> ventas) {
        this.ventas = ventas;
        notifyDataSetChanged();
    }

    public RecyclerAdapterHistorial(Context context, List<Venta> ventas, OnItemClickListenerHistory listener) {
        this.ventas = ventas;
        this.context = context;
        this.listener = listener;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDetailCategoria(ventas.get(position));
    }

    @Override
    public int getItemCount() {
        return ventas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final ImageView select;
        private final ImageView delete;
        private final TextView name;
        private final TextView precio;
        private final TextView cantidad;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_item_shopping);
            name = itemView.findViewById(R.id.tv_item_name);
            precio = itemView.findViewById(R.id.tv_item_precio);
            select = itemView.findViewById(R.id.iv_select_card);
            delete = itemView.findViewById(R.id.iv_delete_card);
            cantidad = itemView.findViewById(R.id.tv_item_cantidad);
        }
        @SuppressLint("SetTextI18n")
        void setDetailCategoria(Venta venta){
            name.setText("IdPedido: "+venta.getId());
            precio.setText("Cantidad de Productos: "+venta.getListProduct().size());
            cantidad.setText("Valor Total: "+ venta.getMontoTotal());
            if (venta.getListProduct() != null && !venta.getListProduct().isEmpty())convertImageService(venta.getListProduct().get(0).getImage(), imageView, 150);
            select.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            imageView.setOnClickListener(v -> {
                listener.onItemClick(venta);
            });
        }
    }
}
