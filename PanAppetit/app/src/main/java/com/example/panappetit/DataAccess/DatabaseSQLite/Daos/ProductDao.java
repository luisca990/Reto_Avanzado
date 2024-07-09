package com.example.panappetit.DataAccess.DatabaseSQLite.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.panappetit.DataAccess.DatabaseSQLite.DatabaseHelper;
import com.example.panappetit.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private SQLiteDatabase db; // Objeto para interactuar con la base de datos
    private final DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    // Constructor que recibe el contexto de la aplicación y crea una instancia de DatabaseHelper
    public ProductDao(Context context) {
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

    public long insertProduct(Product product){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("nombre", product.getNombre()); // Inserción del nombre del producto
        values.put("descripcion", product.getDescripcion()); // Inserción del descripcion del producto
        values.put("precio", product.getPrecio()); // Inserción del precio del producto
        values.put("cantidad_stock", product.getCantidad()); // Inserción del cantidad del producto
        values.put("imagen", product.getImage()); // Inserción del imagen del producto
        return db.insert("usuarios", null, values);
    }

    // Método para obtener todos los usuarios de la tabla 'usuarios'
    public List<Product> getListProducts() {
        List<Product> productos = new ArrayList<>(); // Lista para almacenar los usuarios obtenidos
        Cursor cursor = db.rawQuery("SELECT * FROM productos", null); // Ejecución de la consulta SQL

        // Iteración sobre los resultados del cursor para obtener los datos de cada usuario
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3),
                        cursor.getInt(4),
                        cursor.getString(5)); // Creación de una nueva instancia de Usuario
                product.setId(cursor.getInt(0));
                productos.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cierre del cursor
        return productos; // Retorno de la lista de usuarios
    }
}
