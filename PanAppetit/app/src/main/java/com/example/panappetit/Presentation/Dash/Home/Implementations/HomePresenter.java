package com.example.panappetit.Presentation.Dash.Home.Implementations;

import android.content.Context;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.ProductDao;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Presentation.Dash.Home.Interfaces.IHomePresenter;
import com.example.panappetit.Presentation.Dash.Home.Interfaces.IHomeView;

public class HomePresenter implements IHomePresenter {
    private final IHomeView view;
    private final Context context;
    private final ProductDao dao;

    public HomePresenter(IHomeView view, Context context) {
        this.view = view;
        this.context = context;
        dao = new ProductDao(context);
        dao.openDb();
    }

    @Override
    public void getAllProductsSuccess() {
        view.showGetAllProductsSuccess(Product.getListProduct(dao));
        dao.closeDb();
    }
}
