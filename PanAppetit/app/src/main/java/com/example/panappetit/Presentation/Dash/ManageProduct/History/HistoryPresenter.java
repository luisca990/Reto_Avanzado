package com.example.panappetit.Presentation.Dash.ManageProduct.History;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.VentaDao;
import com.example.panappetit.Models.Venta;
import com.example.panappetit.Presentation.Dash.ManageProduct.History.Interfaces.IHistoryPresenter;
import com.example.panappetit.Presentation.Dash.ManageProduct.History.Interfaces.IHistoryView;

public class HistoryPresenter implements IHistoryPresenter {
    private final IHistoryView view;
    private final VentaDao dao;

    public HistoryPresenter(IHistoryView view, VentaDao dao) {
        this.view = view;
       this.dao = dao;
        dao.openDb();
    }

    @Override
    public void getAllVentas(int idUser) {
        view.getAllVentas(Venta.getAllVentas(dao, idUser));
    }
}
