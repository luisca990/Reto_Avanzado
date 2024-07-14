package com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Implementations;

import android.content.Context;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.PedidoDao;
import com.example.panappetit.Models.Pedido;
import com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Interfaces.IDetailClientPresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Interfaces.IDetailClientView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

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
    public void insertVenta(Pedido pedido) {
        if (!pedido.validateFieldsPedidos()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        view.showActionPedido(pedido.insertPedido(dao, pedido));
        dao.closeDb();
    }

    @Override
    public void updateVenta(Pedido pedido) {
        if (!pedido.validateFieldsPedidos()) {
            view.showDialogAdvertence(R.string.fiels_vacio, context.getString(R.string.mess_fiels_vacio), DialogueGenerico.TypeDialogue.ADVERTENCIA);
        return;
    }
        view.showActionPedido(pedido.updatePedido(dao, pedido));
        dao.closeDb();
    }
}
