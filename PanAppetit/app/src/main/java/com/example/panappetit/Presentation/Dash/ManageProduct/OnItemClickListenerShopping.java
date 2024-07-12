package com.example.panappetit.Presentation.Dash.ManageProduct;

import com.example.panappetit.Models.Product;

public interface OnItemClickListenerShopping {
    void onItemClick(Product product);
    void onItemClickDelete(Product product);
    void onItemClickSelect(Product product);
}
