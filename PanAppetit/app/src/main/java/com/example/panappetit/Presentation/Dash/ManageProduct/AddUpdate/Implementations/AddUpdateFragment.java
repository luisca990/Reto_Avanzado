package com.example.panappetit.Presentation.Dash.ManageProduct.AddUpdate.Implementations;

import static com.example.panappetit.Utils.Util.convertImageService;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.Navigation;
import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.ManageProduct.AddUpdate.Interfaces.IAddUpdateView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

public class AddUpdateFragment extends BaseFragment {
    private AddUpdatePresenter presenter;
    private Product product;
    private ImageView arrow, image;
    private TextView title;
    private EditText url, name, description, count, precio;
    private Button save;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_add_update, container, false));
        presenter = new AddUpdatePresenter(new listenerPresenter(), getContext());
        arrow = getCustomView().findViewById(R.id.iv_back_add_update);
        title = getCustomView().findViewById(R.id.tv_title_add_update);
        image = getCustomView().findViewById(R.id.iv_image_add_update);
        url = getCustomView().findViewById(R.id.et_url);
        name = getCustomView().findViewById(R.id.et_name);
        description = getCustomView().findViewById(R.id.et_descript);
        count = getCustomView().findViewById(R.id.et_count);
        precio = getCustomView().findViewById(R.id.et_valor);
        save = getCustomView().findViewById(R.id.btn_add_update);

        if (getArguments() != null) {
            Product item = getArguments().getParcelable("product");
            if (item != null) {
                this.product = item;
                fillDataFields();
            }
        }
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        arrow.setOnClickListener(v->
            Navigation.findNavController(requireView()).navigateUp());
        save.setOnClickListener(v->{
            Product item = new Product(
                    name.getText().toString(),
                    description.getText().toString(),
                    Float.parseFloat(precio.getText().toString()),
                    Integer.parseInt(count.getText().toString()),
                    url.getText().toString()
            );
            item.setId(product.getId());
            if (product == null){
                presenter.insertProduct(item);
                return;
            }
            product = item;
            presenter.updateProduct(item);
        });
        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                convertImageService(s.toString(), image, 300);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void fillDataFields(){
        title.setText(getString(R.string.actualizar_producto));
        convertImageService(product.getImage(), image, 300);
        url.setText(product.getImage());
        name.setText(product.getNombre());
        description.setText(product.getDescripcion());
        count.setText(product.getCantidad().toString());
        precio.setText(product.getPrecio().toString());
    }
    private void emptyFields(){
        name.setText("");
        description.setText("");
        count.setText("");
        precio.setText("");
    }

    private class listenerPresenter implements IAddUpdateView {

        @Override
        public void showInsertProduct(int id, String name) {
            dialogueFragment(R.string.insertar_usuario, getString(R.string.insert_user)+id+" nombre: "+name, DialogueGenerico.TypeDialogue.OK);
            emptyFields();
        }

        @Override
        public void showUpdateProduct(int id, String name) {
            dialogueFragment(R.string.update_usuario, getString(R.string.update_user)+id+" nombre: "+name, DialogueGenerico.TypeDialogue.OK);
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", product);
            Navigation.findNavController(requireView()).navigate(R.id.action_addUpdateFragment_to_detailFragment, bundle);
        }

        @Override
        public void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type) {
            dialogueFragment(title, message, type);
        }
    }
}