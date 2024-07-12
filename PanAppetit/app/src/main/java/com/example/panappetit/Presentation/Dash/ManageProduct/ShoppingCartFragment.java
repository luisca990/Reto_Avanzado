package com.example.panappetit.Presentation.Dash.ManageProduct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.Home.Adapter.OnItemClickListenerProduct;
import com.example.panappetit.Presentation.Dash.Home.Adapter.RecyclerAdapterProducts;
import com.example.panappetit.Presentation.Dash.Home.Implementations.HomeFragment;
import com.example.panappetit.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends BaseFragment {
    private ImageView arrow;
    private CheckBox checkBox;
    private Button shop;
    private RecyclerAdapterShopping adapter;
    private List<Product> productsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_shopping_cart, container, false));
        arrow = getCustomView().findViewById(R.id.iv_return_shopping);
        checkBox = getCustomView().findViewById(R.id.checkbox_shopping);
        shop = getCustomView().findViewById(R.id.btn_shopping);
        RecyclerView rv = getCustomView().findViewById(R.id.rv_shopping);

        adapter = new RecyclerAdapterShopping(getContext(), productsList, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);

        productsList = new ArrayList<>();
        productsList.add(new Product("Croassant", "Pan relleno de queso y jamon", 1500.0F, 25, "https://tse3.mm.bing.net/th?id=OIP.g1RQBKSF2t_VVIZb35e0ZgHaEY&pid=Api&P=0&h=180"));
        productsList.add(new Product("Pan de queso", "Pan relleno de queso", 300.0F, 50, "https://tse4.explicit.bing.net/th?id=OIP.pL2T6t1FcHp2YQIK5WpjswHaFj&pid=Api&P=0&h=180"));
        productsList.add(new Product("Pan de Bono", "Pan de yuca y queso", 600.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.bqCFFmlaSZMU5lC26q7isQHaHa&pid=Api&P=0&h=180"));
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateList(productsList);
        arrow.setOnClickListener(v->{Navigation.findNavController(requireView()).navigateUp();});
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // El CheckBox está seleccionado
                // Realiza alguna acción aquí
            } else {
                // El CheckBox no está seleccionado
                // Realiza alguna acción aquí
            }
        });
        shop.setOnClickListener(v->{});
    }

    private class listenerAdapter implements OnItemClickListenerShopping {
        @Override
        public void onItemClick(Product product) {

        }

        @Override
        public void onItemClickDelete(Product product) {

        }

        @Override
        public void onItemClickSelect(Product product) {

        }
    }
}