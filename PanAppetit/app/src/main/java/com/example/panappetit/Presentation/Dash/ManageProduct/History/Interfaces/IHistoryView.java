package com.example.panappetit.Presentation.Dash.ManageProduct.History.Interfaces;

import com.example.panappetit.Models.Venta;
import com.example.panappetit.Utils.DialogueGenerico;

import java.util.List;

public interface IHistoryView {
    void getAllVentas(List<Venta> list);
    void showDialogAdvertence(int title, String message, DialogueGenerico.TypeDialogue type);
}
