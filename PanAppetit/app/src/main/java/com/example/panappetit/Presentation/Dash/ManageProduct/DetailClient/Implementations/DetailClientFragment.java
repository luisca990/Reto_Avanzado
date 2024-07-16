package com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Implementations;

import static com.example.panappetit.Utils.Util.convertImageService;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.PedidoDao;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Models.Pedido;
import com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Interfaces.IDetailClientView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

import java.util.Date;

public class DetailClientFragment extends BaseFragment {
    private Product product;
    private DetailClientPresenter presenter;
    private SessionManager sessionManager;
    private ImageView arrow, image, btnRest, btnAdd;
    private TextView name, description, value, countValue, count;
    private Button btnCart;
    private int valueCount = 0;
    private PedidoDao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_detail_client, container, false));
        image = getCustomView().findViewById(R.id.iv_image_detail_client);
        arrow = getCustomView().findViewById(R.id.iv_back_detail_client);
        name = getCustomView().findViewById(R.id.tv_name_detail_client);
        description = getCustomView().findViewById(R.id.tv_descript_detail_client);
        value = getCustomView().findViewById(R.id.tv_value_client);
        count = getCustomView().findViewById(R.id.tv_count_client);
        btnRest = getCustomView().findViewById(R.id.iv_rest_count);
        countValue = getCustomView().findViewById(R.id.tv_count_value);
        btnAdd = getCustomView().findViewById(R.id.iv_add_count);
        btnCart = getCustomView().findViewById(R.id.btn_add_cart);

        if (getArguments() != null) {
            Product product = getArguments().getParcelable("product", Product.class);
            if (product != null) {
                this.product = product; // Show product details immediately
            }
        }
        sessionManager = new SessionManager(requireContext());
        dao = new PedidoDao(getContext());
        presenter = new DetailClientPresenter(new listenerPresenter(), getContext(), dao);
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        arrow.setOnClickListener(v-> Navigation.findNavController(requireView()).navigateUp());

        btnRest.setOnClickListener(v->{
            if(valueCount != 0){
                valueCount--;
                validateCountProduct();
                countValue.setText(String.valueOf(valueCount));
                return;
            }
            Toast.makeText(requireContext(), getString(R.string.rest_cantidad), Toast.LENGTH_SHORT).show();
        });

        btnAdd.setOnClickListener(v->{
            valueCount++;
            validateCountProduct();
            countValue.setText(String.valueOf(valueCount));
        });

        btnCart.setOnClickListener(v->{
            if (valueCount == 0){
                dialogueFragment(R.string.cantidad, getString(R.string.cantidad_cero), DialogueGenerico.TypeDialogue.ADVERTENCIA);
                return;
            }

            validateCountProduct();
            float monto = (valueCount*product.getPrecio()) + sessionManager.getMontoPedido();
            Date date = new Date();
            product.setProductCantidad(valueCount);
            Pedido pedido = new Pedido(sessionManager.getUseId(), date.toString(), monto, product);
            if (sessionManager.getPedidoId() != 0){
                pedido.setId(sessionManager.getPedidoId());
                presenter.updateVenta(pedido);
                return;
            }
            presenter.insertVenta(pedido);
        });
        completeProductData();
    }

    @SuppressLint("SetTextI18n")
    private void validateCountProduct(){
        if (valueCount > product.getCantidad()){
            dialogueFragment(R.string.cantidad, getString(R.string.cantidad_max)+product.getCantidad(), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        int cantidad = (product.getCantidad()-valueCount);
        count.setText(getString(R.string.stock)+cantidad);
    }

    @SuppressLint("SetTextI18n")
    private void completeProductData(){
        if (product != null){
            convertImageService(product.getImage(), image, 300);
            name.setText(product.getNombre());
            description.setText(product.getDescripcion());
            value.setText("$ "+product.getPrecio().toString());
            countValue.setText(String.valueOf(valueCount));
            count.setText(getString(R.string.stock)+product.getCantidad());
        }
    }

    private class listenerPresenter implements IDetailClientView{
        @Override
        public void showActionPedido(int id) {
            Toast.makeText(getContext(), "Añadiste un producto al carrito", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigateUp();
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