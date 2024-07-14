package com.example.panappetit.Presentation.Dash.ManageProduct.Shopping.Interfaces;

import com.example.panappetit.Utils.DialogueGenerico;

public interface IShoppingView {
    void showActionVenta(int id);
    void showActionDelete(Boolean status);
    void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type);
}
