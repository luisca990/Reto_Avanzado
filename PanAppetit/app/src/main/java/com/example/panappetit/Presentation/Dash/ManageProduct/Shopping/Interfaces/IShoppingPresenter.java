package com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Interfaces;

import com.example.panappetit.Models.Venta;

public interface IShoppingPresenter {
    void insertVenta(Venta venta, int idPedido);
    void deleteDetallePedido(int idProduct, int idPedido);
}
