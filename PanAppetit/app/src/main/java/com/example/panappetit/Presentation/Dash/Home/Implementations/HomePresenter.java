package com.example.panappetit.Presentation.Dash.Home.Implementations;

import android.content.Context;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.PedidoDao;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.ProductDao;
import com.example.panappetit.Models.Pedido;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.Home.Interfaces.IHomePresenter;
import com.example.panappetit.Presentation.Dash.Home.Interfaces.IHomeView;

public class HomePresenter implements IHomePresenter {
    private final IHomeView view;
    private final ProductDao dao;
    private final PedidoDao daoP;

    public HomePresenter(IHomeView view, Context context) {
        this.view = view;
        dao = new ProductDao(context);
        dao.openDb();
        daoP = new PedidoDao(context);
        daoP.openDb();
    }

    @Override
    public void getAllProductsSuccess() {
        view.showGetAllProductsSuccess(Product.getListProduct(dao));
        dao.closeDb();
    }

    @Override
    public void getLastPedidoByUserId(int userId) {
        view.showGetLastPedidoSuccess(Pedido.getLastPedidoByUserId(daoP, userId));
        daoP.closeDb();
    }
}
