package com.example.panappetit.DataAccess.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "SessionPref";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "correo";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_VENTA_ID = "idVenta";
    private static final String KEY_DATE = "date";
    private static final String KEY_MONTO = "monto";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void createLoginSession(String email, int userId) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putInt(KEY_USER_ID, userId);
        editor.commit();
    }

    public void setPedido(int id, String date, Float monto){
        editor.putInt(KEY_VENTA_ID, id);
        editor.putString(KEY_DATE, date);
        editor.putFloat(KEY_MONTO, monto);
        editor.commit();
    }

    public void setMonto(Float monto){
        editor.putFloat(KEY_MONTO, monto);
        editor.commit();
    }

    public Float getMontoPedido() {
        return pref.getFloat(KEY_MONTO, 0.0F);
    }

    public int getPedidoId() {
        return pref.getInt(KEY_VENTA_ID, 0);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, null);
    }
    public int getUseId() {
        return pref.getInt(KEY_USER_ID, 0);
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
