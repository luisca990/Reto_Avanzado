package com.example.panappetit.DataAccess.DatabaseSQLite.Daos;

import static com.example.panappetit.Utils.Constants.TABLE_DETALLES;
import static com.example.panappetit.Utils.Constants.TABLE_DETALLES_VENTA;
import static com.example.panappetit.Utils.Constants.TABLE_PEDIDOS;
import static com.example.panappetit.Utils.Constants.TABLE_VENTA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.panappetit.DataAccess.DatabaseSQLite.DatabaseHelper;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.Pedido;
import com.example.panappetit.Models.Venta;
import com.example.panappetit.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class VentaDao {
    private Context context;
    private SQLiteDatabase db; // Objeto para interactuar con la base de datos
    private final DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    // Constructor que recibe el contexto de la aplicación y crea una instancia de DatabaseHelper
    public VentaDao(Context context) {
        this.context = context;
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

    public long insertVenta(Venta venta, int pedidoId){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("usuario_id", venta.getUserID()); // Inserción del userID del Venta
        values.put("monto_total", venta.getMontoTotal()); // Inserción del monto total del Venta

        Long idVenta = db.insert(TABLE_VENTA, null, values);
        if (idVenta == -1){
            return idVenta;
        }
        for (Product item : venta.getListProduct()){
            insertDetalleVenta(item, idVenta);
            deleteDetallePedido(pedidoId, item.getId());
        }
        return idVenta;
    }
    public long insertDetalleVenta(Product product, Long ventaId){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("venta_id", ventaId); // Inserción del pedidoID del DetallePedido
        values.put("producto_id", product.getId());
        values.put("cantidad", product.getProductCantidad());

        return db.insert(TABLE_DETALLES_VENTA, null, values);
    }
    public boolean deleteDetallePedido(int pedidoId, int productId) {
        // La condición WHERE para eliminar un detalle específico
        String whereClause = "pedido_id = ? AND producto_id = ?";
        // Los argumentos para la condición WHERE
        String[] whereArgs = { String.valueOf(pedidoId), String.valueOf(productId) };

        // Realizar la operación de eliminación
        int rowsDeleted = db.delete(TABLE_DETALLES, whereClause, whereArgs);

        // Devuelve true si al menos una fila fue eliminada
        return rowsDeleted > 0;
    }
    public List<Venta> getVentasByUsuarioId(int usuarioId) {
        List<Venta> ventas = new ArrayList<>();
        String query = "SELECT id AS venta_id, monto_total, usuario_id " +
                "FROM venta " +
                "WHERE usuario_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuarioId)});

        if (cursor.moveToFirst()) {
            do {
                int ventaId = cursor.getInt(cursor.getColumnIndexOrThrow("venta_id"));
                float montoTotal = cursor.getFloat(cursor.getColumnIndexOrThrow("monto_total"));
                int usuarioIdFromDB = cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id"));

                Venta venta = new Venta(ventaId, usuarioIdFromDB, montoTotal);
                ventas.add(venta);

                // Obtener productos asociados a esta venta
                List<Product> productos = getProductosByVentaId(ventaId);
                venta.setListProduct(productos);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return ventas;
    }
    private List<Product> getProductosByVentaId(int ventaId) {
        List<Product> productos = new ArrayList<>();
        String query = "SELECT p.id AS producto_id, p.nombre, p.descripcion, p.precio, p.cantidad_stock, p.imagen, d.cantidad AS productCantidad " +
                "FROM productos p " +
                "JOIN detalles_venta d ON p.id = d.producto_id " +
                "WHERE d.venta_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(ventaId)});

        if (cursor.moveToFirst()) {
            do {
                int productoId = cursor.getInt(cursor.getColumnIndexOrThrow("producto_id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
                float precio = cursor.getFloat(cursor.getColumnIndexOrThrow("precio"));
                int cantidadStock = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad_stock"));
                String imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"));
                int productCantidad = cursor.getInt(cursor.getColumnIndexOrThrow("productCantidad"));

                Product product = new Product(nombre, descripcion, precio, cantidadStock, imagen);
                product.setId(productoId);
                product.setProductCantidad(productCantidad);

                productos.add(product);

            } while (cursor.moveToNext());
        } else {
            Log.d("Database", "No detalles found for venta_id: " + ventaId);
        }

        cursor.close();
        return productos;
    }
}
