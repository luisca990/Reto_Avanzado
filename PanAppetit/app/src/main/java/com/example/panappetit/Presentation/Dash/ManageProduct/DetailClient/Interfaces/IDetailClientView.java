package com.example.panappetit.Presentation.Dash.ManageProduct.DetailClient.Interfaces;

import com.example.panappetit.Utils.DialogueGenerico;

public interface IDetailClientView {
    void showActionPedido(int id);
    void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type);
}
