package com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Implementations;

import android.content.Context;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.VentaDao;
import com.example.panappetit.Models.Venta;
import com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Interfaces.IShoppingPresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Interfaces.IShoppingView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

public class ShoppingPresenter implements IShoppingPresenter {
    private final IShoppingView view;
    private final Context context;
    private final VentaDao dao;

    public ShoppingPresenter(IShoppingView view, Context context) {
        this.view = view;
        this.context = context;
        dao = new VentaDao(context);
    }

    @Override
    public void insertVenta(Venta venta, int idPedido) {
        dao.openDb();
        if (!venta.validateFieldsVenta()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showActionVenta(venta.insertVenta(dao, venta, idPedido));
        dao.closeDb();
    }

    @Override
    public void deleteDetallePedido(int idProduct, int idPedido) {
        dao.openDb();
        view.showActionDelete(Venta.deleteDetallePedido(dao, idProduct, idPedido));
        dao.closeDb();
    }
}
