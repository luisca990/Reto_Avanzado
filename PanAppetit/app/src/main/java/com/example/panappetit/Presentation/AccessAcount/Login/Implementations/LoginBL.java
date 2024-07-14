package com.example.panappetit.Presentation.AccessAcount.Login.Implementations;


import android.content.Context;

import androidx.annotation.NonNull;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.UsuarioDao;
import com.example.panappetit.DataAccess.Repositories.IRepository;
import com.example.panappetit.DataAccess.Repositories.RepoLogin;
import com.example.panappetit.DataAccess.Services;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.MessageResponse;
import com.example.panappetit.Models.User;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginBL;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginListener;

public class LoginBL implements ILoginBL {

    private final Context context;
    private final ILoginListener listener;
    private final SessionManager sessionManager;
    private final UsuarioDao dao;


    public LoginBL(Context context, ILoginListener loginListener) {
        this.context = context;
        this.listener = loginListener;
        this.sessionManager = new SessionManager(context);
        this.dao = new UsuarioDao(context);
        dao.openDb();
    }

    @Override
    public void startSection(@NonNull User user) {
        new RepoLogin().LogIn(context, user, new ListenerRepositories(), Services.LOGIN);
    }

    private class ListenerRepositories implements IRepository {

        @Override
        public void onSuccessResponse(Object objectResponse, Services services) {
            if (services == Services.LOGIN) {
                User user = (User) objectResponse;
                listener.responseLogin(user);
                long userID = dao.insertUser(user);
                sessionManager.createLoginSession(user.getEmail(), (int) userID);
                dao.closeDb();
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
