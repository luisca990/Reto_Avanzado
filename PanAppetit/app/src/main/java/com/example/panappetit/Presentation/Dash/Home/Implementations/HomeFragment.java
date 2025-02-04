package com.example.panappetit.Presentation.Dash.Home.Implementations;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.PedidoDao;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.ProductDao;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.Pedido;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.Home.Adapter.OnItemClickListenerProduct;
import com.example.panappetit.Presentation.Dash.Home.Adapter.RecyclerAdapterProducts;
import com.example.panappetit.Presentation.Dash.Home.Interfaces.IHomeView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.Constants;
import com.example.panappetit.Utils.DialogueGenerico;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private HomePresenter presenter;
    private SessionManager sessionManager;
    private List<Product> productsList;
    private RecyclerAdapterProducts adapter;
    private EditText search;
    private TextView shopping;
    private ImageView logout;
    private FloatingActionButton fabAdd, fabCar, fabHistory;
    private ProductDao dao;
    private PedidoDao daoP;
    private String typeUser;
    private Pedido cartPedido = new Pedido();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_home, container, false));
        // Inicialización de la base de datos y conexión
        productsList = new ArrayList<>();
        RecyclerView rv = getCustomView().findViewById(R.id.rvProducts);
        search = getCustomView().findViewById(R.id.searchView);
        logout = getCustomView().findViewById(R.id.iv_logout);
        fabAdd = getCustomView().findViewById(R.id.fab_add);
        fabCar = getCustomView().findViewById(R.id.fab_buy);
        fabHistory = getCustomView().findViewById(R.id.fab_history);
        shopping = getCustomView().findViewById(R.id.notification_badge);

        dao = new ProductDao(getContext());
        daoP = new PedidoDao(getContext());
        presenter = new HomePresenter(new listenerPresenter(), dao, daoP);
        sessionManager = new SessionManager(requireContext());

        adapter = new RecyclerAdapterProducts(getContext(), productsList, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);
        if (getArguments() != null) {
             typeUser = getArguments().getString(Constants.Tag.USER);
        }

        isVisibleButons();
        displaySesion();
        textSearchProduct();
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAllProductsSuccess();
        presenter.getLastPedidoByUserId(sessionManager.getUseId());
        logout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(getContext(), getString(R.string.el_usuario)+sessionManager.getUserEmail()+getString(R.string.se_deslogueo), Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
        });
        fabAdd.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_addUpdateFragment));
        fabCar.setOnClickListener(v->{
            if(cartPedido.getListProduct().isEmpty()){
                dialogueFragment(R.string.shopping, getString(R.string.no_shopping), DialogueGenerico.TypeDialogue.ADVERTENCIA);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("pedido", cartPedido);
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_shoppingCartFragment, bundle);
        });
        fabHistory.setOnClickListener(v-> Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_historyFragment));
    }
    private void isVisibleButons() {
        if (!typeUser.equals(getString(R.string.admin))) {
            fabAdd.setVisibility(View.INVISIBLE);
        }else {
            fabCar.setVisibility(View.INVISIBLE);
            fabHistory.setVisibility(View.INVISIBLE);
        }
    }
    private void displaySesion(){
        if (!sessionManager.isLoggedIn()) {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
            Toast.makeText(getContext(), "El usuario " + sessionManager.getUserEmail() + " no esta logueado", Toast.LENGTH_SHORT).show();
        }
    }
    private void textSearchProduct(){
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    adapter.updateList(productsList);
                    hideKeyboardFragment();
                    return;
                }
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        public void showGetLastPedidoSuccess(Pedido pedido) {
            if (pedido == null || pedido.getListProduct().isEmpty()){
                shopping.setVisibility(View.INVISIBLE);
            }else {
                cartPedido = pedido;
                shopping.setVisibility(View.VISIBLE);
                shopping.setText(String.valueOf(pedido.getListProduct().size()));
            }
        }
    }

    private class listenerAdapter implements OnItemClickListenerProduct {
        @Override
        public void onItemClick(Product product) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", product);
            if (typeUser.equals("admin")) {
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, bundle);
            }else {
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailClientFragment, bundle);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
        daoP.closeDb();
    }
}