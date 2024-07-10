package com.example.panappetit.Presentation.Dash.ManageProduct;

import static com.example.panappetit.Utils.Util.convertImageService;

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
import com.example.panappetit.Models.Product;
import com.example.panappetit.R;

public class DetailFragment extends BaseFragment {
    private Product product;
    private ImageView arrow, image;
    private TextView name, description, count, valor;
    private Button delete, update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_detail, container, false));
        image = getCustomView().findViewById(R.id.iv_image_detail);
        arrow = getCustomView().findViewById(R.id.iv_back_detail);
        TextView title = getCustomView().findViewById(R.id.tv_title_detail);
        name = getCustomView().findViewById(R.id.tv_name_detail);
        description = getCustomView().findViewById(R.id.tv_descript_detail);
        count = getCustomView().findViewById(R.id.tv_title_count_detail);
        valor = getCustomView().findViewById(R.id.tv_valor_detail);
        delete = getCustomView().findViewById(R.id.btn_delete_section);
        update = getCustomView().findViewById(R.id.btn_update_section);

        if (getArguments() != null) {
            Product product = getArguments().getParcelable("product");
            if (product != null) {
                this.product = product; // Show character details immediately
            }
        }
        return getCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        update.setOnClickListener(v->{
            Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();});
        delete.setOnClickListener(v->{
            Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();});
        arrow.setOnClickListener(v->{Navigation.findNavController(requireView()).navigateUp();});
        completeProductData();
    }

    private void completeProductData(){
        if (product != null){
            convertImageService(product.getImage(), image, 300);
            name.setText(product.getNombre());
            description.setText(product.getNombre());
            count.setText(product.getNombre());
            valor.setText(product.getNombre());
        }
    }
}