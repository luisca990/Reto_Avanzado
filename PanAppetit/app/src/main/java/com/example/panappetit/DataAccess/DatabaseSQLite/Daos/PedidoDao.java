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
            SessionManager sessionManager = new SessionManager(context);
            pedido.setId(pedido.getId());
            sessionManager.setPedido(pedido.getId(), pedido.getDate(), pedido.getMontoTotal());
            return insertDetallePedido(pedido, (long) pedido.getId());
        } else {
            return -1; // Indica que la actualización no se realizó correctamente
        }
    }
    public long insertDetallePedido(Pedido pedido, Long pedidoId){
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("pedido_id", pedidoId); // Inserción del pedidoID del DetallePedido
        values.put("producto_id", pedido.getProduct().getId());
        values.put("cantidad", pedido.getProduct().getProductCantidad());
        values.put("precio", pedido.getProduct().getPrecio());

        return db.insert(TABLE_DETALLES, null, values);
    }
    public Pedido getLastPedidoByUserId(int userId) {
        Pedido pedido = null;
        // Primero obtenemos el ID del último pedido del usuario
        String queryPedido = "SELECT " +
                "p.id AS pedido_id, " +
                "p.fecha_pedido, " +
                "p.monto_total " +
                "FROM pedidos p " +
                "WHERE p.usuario_id = ? " +
                "ORDER BY p.fecha_pedido DESC " +
                "LIMIT 1";

        Cursor cursorPedido = db.rawQuery(queryPedido, new String[]{String.valueOf(userId)});
        if (cursorPedido.moveToFirst()) {
            int pedidoId = cursorPedido.getInt(cursorPedido.getColumnIndexOrThrow("pedido_id"));
            String fechaPedido = cursorPedido.getString(cursorPedido.getColumnIndexOrThrow("fecha_pedido"));
            float montoTotal = cursorPedido.getFloat(cursorPedido.getColumnIndexOrThrow("monto_total"));

            // Crear el pedido
            pedido = new Pedido(pedidoId, userId, fechaPedido, montoTotal);
            // Ahora obtenemos todos los detalles del pedido y los productos relacionados
            String queryDetalles = "SELECT " +
                    "d.cantidad, " +
                    "d.precio AS detalle_precio, " +
                    "pr.id AS producto_id, " +
                    "pr.nombre, " +
                    "pr.descripcion, " +
                    "pr.precio AS producto_precio, " +
                    "pr.cantidad_stock, " +
                    "pr.imagen " +
                    "FROM detalles_pedido d " +
                    "JOIN productos pr ON d.producto_id = pr.id " +
                    "WHERE d.pedido_id = ?";
            Cursor cursorDetalles = db.rawQuery(queryDetalles, new String[]{String.valueOf(pedidoId)});

            while (cursorDetalles.moveToNext()) {
                int cantidad = cursorDetalles.getInt(cursorDetalles.getColumnIndexOrThrow("cantidad"));
                int productoId = cursorDetalles.getInt(cursorDetalles.getColumnIndexOrThrow("producto_id"));
                String nombreProducto = cursorDetalles.getString(cursorDetalles.getColumnIndexOrThrow("nombre"));
                String descripcionProducto = cursorDetalles.getString(cursorDetalles.getColumnIndexOrThrow("descripcion"));
                float productoPrecio = cursorDetalles.getFloat(cursorDetalles.getColumnIndexOrThrow("producto_precio"));
                int cantidadStock = cursorDetalles.getInt(cursorDetalles.getColumnIndexOrThrow("cantidad_stock"));
                String imagen = cursorDetalles.getString(cursorDetalles.getColumnIndexOrThrow("imagen"));

                // Crear producto
                Product product = new Product(nombreProducto, descripcionProducto, productoPrecio, cantidadStock, imagen);
                product.setId(productoId);
                product.setProductCantidad(cantidad);

                // Agregar el producto al pedido
                pedido.settListProduct(product);
            }
            cursorDetalles.close();
        }
        cursorPedido.close();
        return pedido;
    }
}
