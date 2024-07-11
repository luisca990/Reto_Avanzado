package com.example.panappetit.Presentation.Dash.ManageProduct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.panappetit.Base.BaseFragment;
import com.example.panappetit.R;

public class AddUpdateFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCustomView(inflater.inflate(R.layout.fragment_add_update, container, false));

        return getCustomView();
    }
}