package com.example.panappetit.Presentation.Dash.ManageProduct.AddUpdate.Implementations;

import android.content.Context;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.ProductDao;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.ManageProduct.AddUpdate.Interfaces.IAddUpdatePresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.AddUpdate.Interfaces.IAddUpdateView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

public class AddUpdatePresenter implements IAddUpdatePresenter {
    private final IAddUpdateView view;
    private final Context context;
    private final ProductDao dao;

    public AddUpdatePresenter(IAddUpdateView view, Context context, ProductDao dao) {
        this.view = view;
        this.context = context;
        this.dao = dao;
        dao.openDb();
    }

    @Override
    public void insertProduct(Product product) {
        if (!product.validateFieldsProduct()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showInsertProduct(product.insertProduct(dao, product), product.getNombre());
    }

    @Override
    public void updateProduct(Product product) {
        if (!product.validateFieldsProduct()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showUpdateProduct(product.updateProduct(dao, product), product.getNombre());
    }
}
