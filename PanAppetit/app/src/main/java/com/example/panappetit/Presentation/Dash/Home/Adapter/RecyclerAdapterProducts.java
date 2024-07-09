package com.example.panappetit.Presentation.Dash.Home.Adapter;

//import static com.example.panappetit.Utils.Util.convertImageService;

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

public class RecyclerAdapterProducts extends RecyclerView.Adapter<RecyclerAdapterProducts.ViewHolder> {

    private List<Product> products;
    private Context context;
    private final OnItemClickListenerProduct listener;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public RecyclerAdapterProducts(Context context, List<Product> products, OnItemClickListenerProduct listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_products, parent, false);
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
        private final ImageView imageView;
        private final TextView name;
        private final TextView precio;
        private final TextView stock;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_character);
            name = itemView.findViewById(R.id.tv_name);
            precio = itemView.findViewById(R.id.tv_precio);
            stock = itemView.findViewById(R.id.tv_stock);

        }
        void setDetailCategoria(Product product){
            name.setText(product.getNombre());
            //convertImageService(product.getImage(), imageView, 150);
            imageView.setOnClickListener(v -> {
                listener.onItemClick(product);
            });
        }
    }
}
