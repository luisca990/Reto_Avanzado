package com.example.panappetit.Presentation.AccessAcount.Register.Interfaces;

import com.example.panappetit.Models.MessageResponse;
import com.example.panappetit.Models.User;
import com.example.panappetit.Utils.DialogueGenerico;

public interface IRegisterUserView {
    void responseRegisterUser(User user);
    void messageError(MessageResponse message);
    void showDialogFragment(int title, int detail, DialogueGenerico.TypeDialogue type);
}
