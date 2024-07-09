package com.example.panappetit.Presentation.AccessAcount.Login.Interfaces;

import com.example.panappetit.Models.User;
import com.example.panappetit.Utils.DialogueGenerico;

public interface ILoginView {
    void responseLogin(User user);
    void credentialsIncorrect();
    void showDialogFragment(int title, int detail, DialogueGenerico.TypeDialogue type);
}
