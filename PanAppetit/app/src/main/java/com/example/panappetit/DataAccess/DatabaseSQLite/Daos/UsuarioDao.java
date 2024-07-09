package com.example.panappetit.DataAccess.DatabaseSQLite.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.panappetit.DataAccess.DatabaseSQLite.DatabaseHelper;
import com.example.panappetit.Models.User;

public class UsuarioDao {
    private SQLiteDatabase db; // Objeto para interactuar con la base de datos
    private final DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    // Constructor que recibe el contexto de la aplicación y crea una instancia de DatabaseHelper
    public UsuarioDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Método para abrir la conexión con la base de datos en modo escritura
    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    // Método para cerrar la conexión con la base de datos
    public void closeDb() {
        dbHelper.close();
    }

    public long insertUser(User user){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("correo", user.getEmail());// Inserción del email del usuario
        return db.insert("usuarios", null, values);
    }
}
