package com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Implementations;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.VentaDao;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.Pedido;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Models.Venta;
import com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Adapter.OnItemClickListenerShopping;
import com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Adapter.RecyclerAdapterShopping;
import com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Interfaces.IShoppingView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends BaseFragment {
    private ShoppingPresenter presenter;
    private ImageView arrow;
    private TextView monto;
    private CheckBox checkBox;
    private Button shop;
    private RecyclerAdapterShopping adapter;
    private final List<Product> productsList = new ArrayList<>();
    private List<Product> productsSelect = new ArrayList<>();
    private Pedido pedido;
    private float total = 0.0F;
    private VentaDao dao;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_shopping_cart, container, false));
        arrow = getCustomView().findViewById(R.id.iv_return_shopping);
        checkBox = getCustomView().findViewById(R.id.checkbox_shopping);
        shop = getCustomView().findViewById(R.id.btn_shopping);
        monto = getCustomView().findViewById(R.id.tv_monto_shopping);
        RecyclerView rv = getCustomView().findViewById(R.id.rv_shopping);

        dao = new VentaDao(getContext());
        presenter = new ShoppingPresenter(new listenerPresenter(), getContext(), dao);

        adapter = new RecyclerAdapterShopping(getContext(), productsList, new listenerAdapter());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rv.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            // Obtener el objeto Pedido
            pedido = bundle.getParcelable("pedido", Pedido.class);
            // Usar el objeto Pedido
            if (pedido != null) {
                productsList.addAll(pedido.getListProduct());
                productsSelect.addAll(pedido.getListProduct());
                adapter.updateList(productsList);
                for (Product item : productsList){
                    total += item.getPrecio() * item.getProductCantidad();
                }
                monto.setText(getString(R.string.monto_total)+ total);
            }
        }

        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        arrow.setOnClickListener(v->{Navigation.findNavController(requireView()).navigateUp();});
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // El CheckBox está seleccionado
                updateSelectedChecked(true);
                calculeMontoPedido();
            } else {
                // El CheckBox no está seleccionado
                updateSelectedChecked(false);
                calculeMontoPedido();
            }
        });
        shop.setOnClickListener(v->{
            if (productsSelect.isEmpty()){
                dialogueFragment(R.string.fields_empty, "Debe estar selccionado por lo menos un Producto", DialogueGenerico.TypeDialogue.ADVERTENCIA);
                return;
            }
            SessionManager sessionManager = new SessionManager(requireContext());
            Venta venta = new Venta(sessionManager.getUseId(), total);
            venta.setListProduct(productsSelect);
            for (Product item : productsSelect){
                productsList.remove(item);
            }
            float value = 0F;
            for (Product item : productsList){
                value += item.getPrecio() * item.getProductCantidad();
            }
            sessionManager.setMonto(value);
            presenter.insertVenta(venta, pedido.getId());
        });
    }

    private void updateProductInList(int productId, Boolean status) {
        for (Product product : productsSelect) {
            if (product.getId() == productId) {
                // Modificar el dato del producto
                product.setSelected(status);
                productsSelect.remove(product);
                break;
            }
        }
    }

    private void updateSelectedChecked(Boolean status){
        for (Product product : productsList) {
            product.setSelected(status);
            if (status){
                productsSelect.add(product);
            }else {
                productsSelect.remove(product);
            }
        }
        adapter.updateList(productsList);
    }

    @SuppressLint("SetTextI18n")
    private void calculeMontoPedido(){
        total = 0.0F;
        for (Product product : productsList) {
            if (product.getSelected()) {
                total += (product.getPrecio() * product.getProductCantidad());
            }
        }
        monto.setText(getString(R.string.monto_total)+total);
    }

    private class listenerAdapter implements OnItemClickListenerShopping {

        @Override
        public void onItemClickDelete(Product product) {
            productsList.remove(product);
            productsSelect = productsList;
            adapter.updateList(productsList);
            presenter.deleteDetallePedido(product.getId(), pedido.getId());
        }

        @Override
        public void onItemClickSelect(Product product) {
            updateProductInList(product.getId(), !product.getSelected());
            adapter.updateList(productsList);
            calculeMontoPedido();
        }
    }
    private class listenerPresenter implements IShoppingView{
        @Override
        public void showActionVenta(int id) {
            Toast.makeText(getContext(), "Compra Exitosa", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigateUp();
        }

        @Override
        public void showActionDelete(Boolean status) {
            Toast.makeText(getContext(), "Lo eliminaste correctamente", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, message, type);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.closeDb();
    }
}