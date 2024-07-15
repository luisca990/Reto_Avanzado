package com.example.panappetit.DataAccess.DatabaseSQLite.Daos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.panappetit.Utils.Constants.TABLE_USUARIOS;
import com.example.panappetit.DataAccess.DatabaseSQLite.DatabaseHelper;
import com.example.panappetit.Models.Pedido;
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

    public Pedido getPedidoIdByUsuarioId(Long usuarioId) {
        Pedido pedido = null;
        String query = "SELECT p.id, p.usuario_id, p.fecha_pedido, p.monto_total FROM pedidos AS p WHERE usuario_id = ? LIMIT 1";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuarioId)});

        if (cursor.moveToFirst()) {
            int pedidoId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id"));
            String fechaPedido = cursor.getString(cursor.getColumnIndexOrThrow("fecha_pedido"));
            float montoTotal = cursor.getFloat(cursor.getColumnIndexOrThrow("monto_total"));

            pedido = new Pedido(pedidoId, userId, fechaPedido, montoTotal);
        } else {
            Log.d("Database", "No pedido found for usuario_id: " + usuarioId);
        }

        cursor.close();
        return pedido;
    }
}
