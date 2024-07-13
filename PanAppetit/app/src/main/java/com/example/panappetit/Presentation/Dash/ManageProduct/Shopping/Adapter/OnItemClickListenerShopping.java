package com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Adapter;

import com.example.panappetit.Models.Product;

public interface OnItemClickListenerShopping {
    void onItemClick(Product product);
    void onItemClickDelete(Product product);
    void onItemClickSelect(Product product);
}
