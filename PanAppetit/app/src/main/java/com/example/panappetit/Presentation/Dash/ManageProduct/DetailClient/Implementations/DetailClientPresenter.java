package com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Implementations;

import android.content.Context;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.PedidoDao;
import com.example.panappetit.Models.Pedido;
import com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Interfaces.IDetailClientPresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Interfaces.IDetailClientView;

public class DetailClientPresenter implements IDetailClientPresenter {
    private final IDetailClientView view;
    private final Context context;
    private final PedidoDao dao;

    public DetailClientPresenter(IDetailClientView view, Context context) {
        this.view = view;
        this.context = context;
        dao = new PedidoDao(context);
        dao.openDb();
    }

    @Override
    public void insertVenta(Pedido venta) {
        view.showActionVenta(venta.insertPedido(dao, venta));
        dao.closeDb();
    }

    @Override
    public void updateVenta(Pedido venta) {
        view.showActionVenta(venta.updatePedido(dao, venta));
        dao.closeDb();
    }
}
