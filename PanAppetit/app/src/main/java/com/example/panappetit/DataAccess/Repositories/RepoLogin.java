package com.example.panappetit.DataAccess.Repositories;

import android.content.Context;

import com.example.panappetit.DataAccess.Firebase.Login;
import com.example.panappetit.DataAccess.Services;
import com.example.panappetit.Models.MessageResponse;
import com.example.panappetit.Models.User;

public class RepoLogin {
    public <T extends IRepository> void LogIn(Context context, User user, T responder, Services servicio) {
        new Login(context,
                () -> responder.onSuccessResponse(user, servicio),
                () -> responder.onFailedResponse(new MessageResponse(), servicio)
        ).loginWithUser(user);
    }

    public <T extends IRepository> void RegisterUser(Context context,User user, T responder, Services servicio) {
        new Login(context,
                () -> responder.onSuccessResponse(user, servicio),
                () -> responder.onFailedResponse(new MessageResponse(), servicio)
        ).registerUser(user);
    }
}
