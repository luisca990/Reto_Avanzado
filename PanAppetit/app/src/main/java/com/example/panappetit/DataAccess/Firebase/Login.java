package com.example.panappetit.DataAccess.Firebase;

import android.content.Context;
import android.util.Log;

import com.example.panappetit.Models.User;
import com.example.panappetit.R;
import com.example.panappetit.Utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

public class Login {
    private final Runnable messageSuccess;
    private final Runnable failure;
    private final Context context;

    public Login(Context context, Runnable messageSuccess, Runnable failure) {
        this.messageSuccess = messageSuccess;
        this.failure = failure;
        this.context = context;
    }

    public void loginWithUser(User user) {
        FirebaseAuth instanceFirebase = FirebaseAuth.getInstance();
        instanceFirebase.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageSuccess.run();
                        return;
                    }
                    Log.e(Constants.Tag.LOGIN, context.getString(R.string.firebase_login), task.getException());
                    failure.run();
                });
    }

    public void registerUser(User user) {
        FirebaseAuth instanceFirebase = FirebaseAuth.getInstance();
        instanceFirebase.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messageSuccess.run();
                        return;
                    }
                    Log.e(Constants.Tag.REGISTER, context.getString(R.string.firebase_register), task.getException());
                    failure.run();
                });
    }
}
