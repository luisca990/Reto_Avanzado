package com.example.panappetit.Presentation.AccessAcount.Login.Interfaces;

import com.example.panappetit.Models.User;

public interface ILoginListener {
    void responseLogin(User user);
    void credentialsIncorrect();
}
