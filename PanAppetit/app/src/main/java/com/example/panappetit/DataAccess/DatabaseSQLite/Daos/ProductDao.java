package com.example.panappetit.DataAccess.DatabaseSQLite.Daos;

import static com.example.panappetit.Utils.Constants.TABLE_PRODUCTS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        return db.insert(TABLE_PRODUCTS, null, values);
    }
    public long updateProduct(Product product){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a actualizar
        values.put("nombre", product.getNombre()); // Actualización del nombre del producto
        values.put("descripcion", product.getDescripcion()); // Actualización de la descripcion del producto
        values.put("precio", product.getPrecio()); // Actualización del precio del producto
        values.put("cantidad_stock", product.getCantidad()); // Actualización de la cantidad del producto
        values.put("imagen", product.getImage()); // Actualización de la imagen del producto

        // Definición de la condición para la actualización (en este caso, el ID del producto)
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(product.getId()) };

        // Realización de la actualización y obtención del número de filas afectadas
        int count = db.update(TABLE_PRODUCTS, values, selection, selectionArgs);

        // Si la actualización fue exitosa, devolver el ID del producto
        if (count > 0) {
            return product.getId();
        } else {
            return -1; // Indica que la actualización no se realizó correctamente
        }
    }
    public boolean deleteProduct(long productId) {
        // Verificación del ID del producto
        if (productId <= 0) {
            Log.e("Database", "ID del producto no válido.");
            return false;
        }

        // Definición de la condición para la eliminación (en este caso, el ID del producto)
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(productId)};

        // Realización de la eliminación y obtención del número de filas afectadas
        int count = -1;
        try {
            count = db.delete(TABLE_PRODUCTS, selection, selectionArgs);
        } catch (Exception e) {
            Log.e("Database", "Error al eliminar el producto: " + e.getMessage());
            return false; // Indica que la eliminación no se realizó correctamente
        }

        // Si la eliminación fue exitosa, devolver true
        if (count > 0) {
            Log.d("Database", "Producto eliminado con ID: " + productId);
            return true;
        } else {
            Log.e("Database", "No se eliminó ninguna fila para el producto con ID: " + productId);
            return false; // Indica que la eliminación no se realizó correctamente
        }
    }
    // Método para obtener todos los productos de la tabla 'productos'
    public List<Product> getListProducts() {
        List<Product> productos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PRODUCTS, null); // Ejecución de la consulta SQL

        // Iteración sobre los resultados del cursor para obtener los datos de cada usuario
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3),
                        cursor.getInt(4),
                        cursor.getString(5));
                product.setId(cursor.getInt(0));
                productos.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cierre del cursor
        return productos;
    }
}
