package com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Interfaces;

import com.example.panappetit.Models.Pedido;

public interface IDetailClientPresenter {
    void insertVenta(Pedido pedido);
    void updateVenta(Pedido pedido);
}
