package com.example.panappetit.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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
}
