package com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Adapter;

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

import com.example.panappetit.Models.Product;
import com.example.panappetit.R;

import java.util.List;

public class RecyclerAdapterShopping extends RecyclerView.Adapter<RecyclerAdapterShopping.ViewHolder> {

    private List<Product> products;
    private final Context context;
    private final OnItemClickListenerShopping listener;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public RecyclerAdapterShopping(Context context, List<Product> products, OnItemClickListenerShopping listener) {
        this.products = products;
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
        holder.setDetailCategoria(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Boolean status = false;
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
        void setDetailCategoria(Product product){
            name.setText(product.getNombre());
            precio.setText(product.getPrecio().toString());
            cantidad.setText(String.valueOf(product.getProductCantidad()));
            convertImageService(product.getImage(), imageView, 150);
            changeSelectedShopping(product.getSelected());
            select.setOnClickListener(v -> {
                listener.onItemClickSelect(product);
            });
            delete.setOnClickListener(v -> listener.onItemClickDelete(product));
        }
        private void changeSelectedShopping(Boolean change){
            if (change) {
                select.setImageResource(R.mipmap.ic_select_cart_purple);
            } else {
                select.setImageResource(R.mipmap.ic_select_cart_gray);
            }
        }
    }
}
