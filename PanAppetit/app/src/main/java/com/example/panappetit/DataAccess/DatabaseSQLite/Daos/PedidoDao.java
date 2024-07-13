package com.example.panappetit.DataAccess.DatabaseSQLite.Daos;

import static com.example.panappetit.Utils.Constants.TABLE_DETALLES;
import static com.example.panappetit.Utils.Constants.TABLE_PEDIDOS;
import static com.example.panappetit.Utils.Constants.TABLE_PRODUCTS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.panappetit.DataAccess.DatabaseSQLite.DatabaseHelper;
import com.example.panappetit.DataAccess.SharedPreferences.SessionManager;
import com.example.panappetit.Models.Product;
import com.example.panappetit.Models.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoDao {
    private Context context;
    private SQLiteDatabase db; // Objeto para interactuar con la base de datos
    private final DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    // Constructor que recibe el contexto de la aplicación y crea una instancia de DatabaseHelper
    public PedidoDao(Context context) {
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

    public long insertPedido(Pedido pedido){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("usuario_id", pedido.getUserID()); // Inserción del userID del Pedido
        values.put("fecha_pedido", pedido.getDate()); // Inserción del date del Pedido
        values.put("monto_total", pedido.getMontoTotal()); // Inserción del monto total del Pedido

        Long idPedido = db.insert(TABLE_PEDIDOS, null, values);
        if (idPedido == -1){
            return idPedido;
        }
        SessionManager sessionManager = new SessionManager(context);
        pedido.setId(Integer.parseInt(idPedido.toString()));
        sessionManager.setPedido(pedido.getId(), pedido.getDate(), pedido.getMontoTotal());
        return insertDetallePedido(pedido, idPedido);
    }
    public long updatePedido(Pedido pedido){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a actualizar
        values.put("monto_total", pedido.getMontoTotal()); // Actualización del nombre del pedido

        // Definición de la condición para la actualización (en este caso, el ID del pedido)
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(pedido.getId()) };

        // Realización de la actualización y obtención del número de filas afectadas
        int count = db.update(TABLE_PEDIDOS, values, selection, selectionArgs);

        // Si la actualización fue exitosa, devolver el ID del pedido
        if (count > 0) {
            return insertDetallePedido(pedido, (long) pedido.getId());
        } else {
            return -1; // Indica que la actualización no se realizó correctamente
        }
    }
    public long insertDetallePedido(Pedido pedido, Long pedidoId){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("pedido_id", pedidoId); // Inserción del pedidoID del DetallePedido
        values.put("producto_id", pedido.getProduct().getId());
        values.put("cantidad", pedido.getProductCantidad());
        values.put("precio", pedido.getProduct().getPrecio());

        return db.insert(TABLE_DETALLES, null, values);
    }
    public Pedido getLastPedidoByUserId(int userId) {
        Pedido pedido = null;
        String query = "SELECT " +
                "p.id AS pedido_id, " +
                "p.fecha_pedido, " +
                "p.monto_total, " +
                "d.cantidad, " +
                "d.precio AS detalle_precio, " +
                "pr.id AS producto_id, " +
                "pr.nombre, " +
                "pr.descripcion, " +
                "pr.precio AS producto_precio, " +
                "pr.cantidad_stock, " +
                "pr.imagen " +
                "FROM pedidos p " +
                "JOIN detalles d ON p.id = d.pedido_id " +
                "JOIN productos pr ON d.producto_id = pr.id " +
                "WHERE p.usuario_id = ? " +
                "ORDER BY p.fecha_pedido DESC " +
                "LIMIT 1";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

//        if (cursor.moveToFirst()) {
//            int pedidoId = cursor.getInt(cursor.getColumnIndexOrThrow("pedido_id"));
//            String fechaPedido = cursor.getString(cursor.getColumnIndexOrThrow("fecha_pedido"));
//            float montoTotal = cursor.getFloat(cursor.getColumnIndexOrThrow("monto_total"));
//
//            // Crear el pedido
//            pedido = new Pedido(pedidoId, userId, fechaPedido, montoTotal);
//
//            do {
//                int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"));
//                float detallePrecio = cursor.getFloat(cursor.getColumnIndexOrThrow("detalle_precio"));
//
//                int productoId = cursor.getInt(cursor.getColumnIndexOrThrow("producto_id"));
//                String nombreProducto = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
//                String descripcionProducto = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
//                float productoPrecio = cursor.getFloat(cursor.getColumnIndexOrThrow("producto_precio"));
//                int cantidadStock = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad_stock"));
//                String imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"));
//
//                // Crear producto
//                Product product = new Product(productoId, nombreProducto, descripcionProducto, productoPrecio, cantidadStock, imagen);
//                product.setCantidad(cantidad);
//
//                // Agregar el producto al pedido
//                pedido.addProduct(product, cantidad);
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
        return pedido;
    }
    public List<Pedido> getListPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        return pedidos;
    }
}
