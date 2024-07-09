package com.example.panappetit.Presentation.AccessAcount.Login.Implementations;

import static com.example.panappetit.Utils.Util.isNetworkAvailable;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.panappetit.Models.User;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginBL;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginListener;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginPresenter;
import com.example.panappetit.Presentation.AccessAcount.Login.Interfaces.ILoginView;
import com.example.panappetit.R;
import com.example.panappetit.Utils.DialogueGenerico;

public class LoginPresenter implements ILoginPresenter {

    private final Context context;
    private final ILoginView loginView;
    private final ILoginBL bL;

    public LoginPresenter(@NonNull Context context, @NonNull ILoginView view) {
        this.context = context;
        this.loginView = view;
        this.bL = new LoginBL(context, new Listener());
    }
    @Override
    public void startSection(@NonNull User user) {
        if (!isNetworkAvailable(context)) {
            return;
        }
        if (!user.validateFieldsUser()) {
            loginView.showDialogFragment(R.string.fields_empty, R.string.details_fields_empty, DialogueGenerico.TypeDialogue.ADVERTENCIA);
            return;
        }
        bL.startSection(user);
    }

    private class Listener implements ILoginListener {
        @Override
        public void credentialsIncorrect() {
            loginView.credentialsIncorrect();
        }

        @Override
        public void responseLogin(@NonNull User user) {
            loginView.responseLogin(user);
        }
    }
}
