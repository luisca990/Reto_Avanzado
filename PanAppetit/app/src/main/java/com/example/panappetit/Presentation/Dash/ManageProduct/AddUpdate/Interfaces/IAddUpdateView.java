package com.example.panappetit.Presentation.Dash.ManageProduct.AddUpdate.Interfaces;

import com.example.panappetit.Utils.DialogueGenerico;

public interface IAddUpdateView {
    void showInsertProduct(int id, String name);
    void showUpdateProduct(int id, String name);
    void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type);
}
