package com.example.panappetit.Presentation.Dash.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.Home.Adapter.OnItemClickListenerProduct;
import com.example.panappetit.Presentation.Dash.Home.Adapter.RecyclerAdapterProducts;
import com.example.panappetit.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private final List<Product> products = new ArrayList<>();
    private RecyclerAdapterProducts adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_home, container, false));
        RecyclerView rv = getCustomView().findViewById(R.id.rvProducts);

        adapter = new RecyclerAdapterProducts(getContext(), products, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        llenarRecycler();
    }

    private void llenarRecycler(){
        products.add(new Product("Croassant", "", 1500.0F, 25, "https://tse3.mm.bing.net/th?id=OIP.g1RQBKSF2t_VVIZb35e0ZgHaEY&pid=Api&P=0&h=180"));
        products.add(new Product("Pan de queso", "", 300.0F, 50, "https://tse4.explicit.bing.net/th?id=OIP.pL2T6t1FcHp2YQIK5WpjswHaFj&pid=Api&P=0&h=180"));
        products.add(new Product("Pan de Bono", "", 600.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.bqCFFmlaSZMU5lC26q7isQHaHa&pid=Api&P=0&h=180"));
        products.add(new Product("Cucas", "", 1000.0F, 100, "https://tse4.mm.bing.net/th?id=OIP.qTqdQmIh25mqunf4TyzOhwHaEw&pid=Api&P=0&h=180"));
        products.add(new Product("Dedo de queso", "", 1000.0F, 100, "https://tse2.mm.bing.net/th?id=OIP.OCh4XKDlKcPvzTfqMP3gqgAAAA&pid=Api&P=0&h=180"));
        products.add(new Product("Pan Frances", "", 1000.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.ViLDpRzxlgTJ-phhP4usZQHaE8&pid=Api&P=0&h=180"));
        products.add(new Product("Pan de queso", "", 300.0F, 50, "https://tse4.explicit.bing.net/th?id=OIP.pL2T6t1FcHp2YQIK5WpjswHaFj&pid=Api&P=0&h=180"));
        products.add(new Product("Pan de Bono", "", 600.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.bqCFFmlaSZMU5lC26q7isQHaHa&pid=Api&P=0&h=180"));
        products.add(new Product("Cucas", "", 1000.0F, 100, "https://tse4.mm.bing.net/th?id=OIP.qTqdQmIh25mqunf4TyzOhwHaEw&pid=Api&P=0&h=180"));
        products.add(new Product("Dedo de queso", "", 1000.0F, 100, "https://tse2.mm.bing.net/th?id=OIP.OCh4XKDlKcPvzTfqMP3gqgAAAA&pid=Api&P=0&h=180"));
        products.add(new Product("Pan Frances", "", 1000.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.ViLDpRzxlgTJ-phhP4usZQHaE8&pid=Api&P=0&h=180"));
        products.add(new Product("Pan de queso", "", 300.0F, 50, "https://tse4.explicit.bing.net/th?id=OIP.pL2T6t1FcHp2YQIK5WpjswHaFj&pid=Api&P=0&h=180"));
        products.add(new Product("Pan de Bono", "", 600.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.bqCFFmlaSZMU5lC26q7isQHaHa&pid=Api&P=0&h=180"));
        products.add(new Product("Cucas", "", 1000.0F, 100, "https://tse4.mm.bing.net/th?id=OIP.qTqdQmIh25mqunf4TyzOhwHaEw&pid=Api&P=0&h=180"));
        products.add(new Product("Dedo de queso", "", 1000.0F, 100, "https://tse2.mm.bing.net/th?id=OIP.OCh4XKDlKcPvzTfqMP3gqgAAAA&pid=Api&P=0&h=180"));
        products.add(new Product("Pan Frances", "", 1000.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.ViLDpRzxlgTJ-phhP4usZQHaE8&pid=Api&P=0&h=180"));

        adapter.updateList(products);
    }

    private static class listenerAdapter implements OnItemClickListenerProduct {
        @Override
        public void onItemClick(Product product) {

        }
    }
}