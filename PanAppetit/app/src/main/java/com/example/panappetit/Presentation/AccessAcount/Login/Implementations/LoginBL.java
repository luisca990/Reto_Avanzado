package com.example.panappetit.Presentation.AccessAcount.Login.Implementations;


import android.content.Context;

import androidx.annotation.NonNull;

import com.example.panappetit.DataAccess.Repositories.IRepository;
import com.example.panappetit.DataAccess.Repositories.RepoLogin;
import com.example.panappetit.DataAccess.Services;
import com.example.panappetit.Models.MessageResponse;
import com.example.panappetit.Models.User;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginBL;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginListener;

public class LoginBL implements ILoginBL {

    private final Context context;
    private final ILoginListener listener;

    public LoginBL(Context context, ILoginListener loginListener) {
        this.context = context;
        this.listener = loginListener;
    }

    @Override
    public void startSection(@NonNull User user) {
        new RepoLogin().LogIn(context, user, new ListenerRepositories(), Services.LOGIN);
    }

    private class ListenerRepositories implements IRepository {

        @Override
        public void onSuccessResponse(Object objectResponse, Services services) {
            if (services == Services.LOGIN) {
                listener.responseLogin((User) objectResponse);
            }
        }

        @Override
        public void onFailedResponse(MessageResponse response, Services services) {
            if (services == Services.LOGIN) {
                listener.credentialsIncorrect();
            }
        }
    }
}
