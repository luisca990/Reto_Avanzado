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

    public HomePresenter(IHomeView view, ProductDao dao, PedidoDao daoP) {
        this.view = view;
        this.dao = dao;
        this.daoP = daoP;
        this.dao.openDb();
        this.daoP.openDb();
    }

    @Override
    public void getAllProductsSuccess() {
        view.showGetAllProductsSuccess(Product.getListProduct(dao));
    }

    @Override
    public void getLastPedidoByUserId(int userId) {
        view.showGetLastPedidoSuccess(Pedido.getLastPedidoByUserId(daoP, userId));
    }
}
