package com.example.panappetit.Presentation.Dash.Home.Implementations;

import static com.example.panappetit.Utils.Util.hideKeyboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.ProductDao;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.MessageResponse;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.Home.Adapter.OnItemClickListenerProduct;
import com.example.panappetit.Presentation.Dash.Home.Adapter.RecyclerAdapterProducts;
import com.example.panappetit.Presentation.Dash.Home.Interfaces.IHomeView;
import com.example.panappetit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends BaseFragment {
    private HomePresenter presenter;
    private SessionManager sessionManager;
    private ProductDao dao;
    private List<Product> productsList;
    private RecyclerAdapterProducts adapter;
    private SearchView search;
    private ImageView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_home, container, false));
        // Inicialización de la base de datos y conexión
        productsList = new ArrayList<>();
        RecyclerView rv = getCustomView().findViewById(R.id.rvProducts);
        search = getCustomView().findViewById(R.id.searchView);
        logout = getCustomView().findViewById(R.id.iv_logout);
        presenter = new HomePresenter(new listenerPresenter(), getContext());
        sessionManager = new SessionManager(requireContext());

        adapter = new RecyclerAdapterProducts(getContext(), productsList, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);

        displaySesion();
        textSearchProduct();
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAllProductsSuccess();
        logout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(getContext(), "El usuario "+sessionManager.getUserEmail()+" se deslogueo", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
        });
    }
    private void displaySesion(){
        if (!sessionManager.isLoggedIn()) Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
        Toast.makeText(getContext(), "El usuario "+sessionManager.getUserEmail()+" no esta logueado", Toast.LENGTH_SHORT).show();
    }

    private void textSearchProduct(){
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    adapter.updateList(productsList);
                    hideKeyboardFragment();
                    return false;
                }
                adapter.filter(newText);
                return false;
            }
        });
    }

    private class listenerPresenter implements IHomeView{
        @Override
        public void showGetAllProductsSuccess(List<Product> products) {
            productsList = products;
            adapter.updateList(products);
        }

        @Override
        public void showMainError(MessageResponse message) {

        }
    }

    private static class listenerAdapter implements OnItemClickListenerProduct {
        @Override
        public void onItemClick(Product product) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }
}