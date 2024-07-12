package com.example.panappetit.Presentation.Dash.ManageProduct.AddUpdate.Interfaces;

import com.example.panappetit.Models.Product;

public interface IAddUpdatePresenter {
    void insertProduct(Product product);
    void updateProduct(Product product);
}
