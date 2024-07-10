package com.example.panappetit.DataAccess.DatabaseSQLite.Daos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static com.example.panappetit.Utils.Constants.TABLE_USUARIOS;
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

    public long insertUser(User user){// Verifica si el correo ya existe en la base de datos
        long userId = getUserIdByEmail(user.getEmail());

        if (userId != -1) {
            // Si el usuario ya existe, devuelve el ID del usuario
            return userId;
        }else {
            ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
            values.put("correo", user.getEmail());// Inserción del email del usuario
            return db.insert(TABLE_USUARIOS, null, values);
        }
    }
    @SuppressLint("Range")
    public long getUserIdByEmail(String email) {
        long userId = -1;
        try (Cursor cursor = db.query(TABLE_USUARIOS, new String[]{"id"}, "correo = ?", new String[]{email}, null, null, null)) {
            // Consulta para buscar el usuario por correo
            if (cursor != null && cursor.moveToFirst()) {
                userId = cursor.getLong(cursor.getColumnIndex("id"));
            }
        } catch (Exception e) {
            // Manejo de excepciones
            e.printStackTrace();
        }
        // Cierra el cursor
        return userId;
    }
}
