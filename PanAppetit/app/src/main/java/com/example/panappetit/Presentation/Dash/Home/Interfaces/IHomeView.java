package com.example.panappetit.Presentation.Dash.Home.Interfaces;


import com.example.panappetit.Models.MessageResponse;
import com.example.panappetit.Models.Product;

import java.util.List;

public interface IHomeView {
    void showGetAllProductsSuccess(List<Product> products);
    void showMainError(MessageResponse message);
}
