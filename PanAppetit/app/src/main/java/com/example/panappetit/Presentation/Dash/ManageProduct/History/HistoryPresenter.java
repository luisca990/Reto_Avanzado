package com.example.panappetit.Presentation.Dash.ManageProduct.History;

import android.content.Context;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.VentaDao;
import com.example.panappetit.Models.Venta;
import com.example.panappetit.Presentation.Dash.ManageProduct.History.Interfaces.IHistoryPresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.History.Interfaces.IHistoryView;
import com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Interfaces.IShoppingPresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Interfaces.IShoppingView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

public class HistoryPresenter implements IHistoryPresenter {
    private final IHistoryView view;
    private final Context context;
    private final VentaDao dao;

    public HistoryPresenter(IHistoryView view, Context context) {
        this.view = view;
        this.context = context;
        dao = new VentaDao(context);
        dao.openDb();
    }

    @Override
    public void getAllVentas(int idUser) {
        view.getAllVentas(Venta.getAllVentas(dao, idUser));
        dao.closeDb();
    }
}
