package com.example.panappetit.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class Util {
    public static void showDialogueGenerico(Context context) {
        if (!(context instanceof AppCompatActivity)) {
            return;
        }

        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        DialogueGenerico.getInstance().showDialogue(appCompatActivity.getSupportFragmentManager(), "DialogoGenerico");
    }
    public static Boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
    public static void convertImageService(String url, ImageView viewImage, int size) {
        try {
            Picasso
                    .get()
                    .load(url)
                    .centerCrop()
                    .resize(size, size)
                    .into(viewImage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void hideKeyboard(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
