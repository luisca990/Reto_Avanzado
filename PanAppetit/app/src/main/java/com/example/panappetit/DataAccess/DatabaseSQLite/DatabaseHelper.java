package com.example.panappetit.DataAccess.DatabaseSQLite;

import static com.example.panappetit.Utils.Constants.CREATE_TABLE_DETALLES;
import static com.example.panappetit.Utils.Constants.CREATE_TABLE_DETALLES_VENTA;
import static com.example.panappetit.Utils.Constants.CREATE_TABLE_PEDIDOS;
import static com.example.panappetit.Utils.Constants.CREATE_TABLE_PRODUCTS;
import static com.example.panappetit.Utils.Constants.CREATE_TABLE_USUARIOS;
import static com.example.panappetit.Utils.Constants.CREATE_TABLE_VENTA;
import static com.example.panappetit.Utils.Constants.TABLE_DETALLES;
import static com.example.panappetit.Utils.Constants.TABLE_PEDIDOS;
import static com.example.panappetit.Utils.Constants.TABLE_PRODUCTS;
import static com.example.panappetit.Utils.Constants.TABLE_USUARIOS;
import static com.example.panappetit.Utils.Constants.TABLE_VENTA;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.panappetit.Models.Product;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "panappetit.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_VENTA);
        db.execSQL(CREATE_TABLE_DETALLES_VENTA);
        db.execSQL(CREATE_TABLE_PEDIDOS);
        db.execSQL(CREATE_TABLE_DETALLES);
        insertDefaultProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DETALLES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PEDIDOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PEDIDOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_VENTA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCTS);
        onCreate(db);
    }

    private void insertDefaultProducts(SQLiteDatabase db) {
        insertProduct(db, new Product("Croassant", "Pan relleno de queso y jamon", 1500.0F, 25, "https://tse3.mm.bing.net/th?id=OIP.g1RQBKSF2t_VVIZb35e0ZgHaEY&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Pan de queso", "Pan relleno de queso", 300.0F, 50, "https://tse4.explicit.bing.net/th?id=OIP.pL2T6t1FcHp2YQIK5WpjswHaFj&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Pan de Bono", "Pan de yuca y queso", 600.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.bqCFFmlaSZMU5lC26q7isQHaHa&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Cucas", "Gallena negra", 1000.0F, 100, "https://tse4.mm.bing.net/th?id=OIP.qTqdQmIh25mqunf4TyzOhwHaEw&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Dedo de queso", "Relleno de queso envuelto de masa", 1000.0F, 100, "https://tse2.mm.bing.net/th?id=OIP.OCh4XKDlKcPvzTfqMP3gqgAAAA&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Pan Frances", "", 1000.0F, 100, "https://tse1.mm.bing.net/th?id=OIP.ViLDpRzxlgTJ-phhP4usZQHaE8&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Pan Blanco", "Pan blanco fresco", 300.0F, 90, "https://tse3.mm.bing.net/th?id=OIP.h36zRcdVV8ytoc3rp9KsXAAAAA&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Pan Integral", "Pan integral saludable", 1500.0F, 25, "https://tse1.mm.bing.net/th?id=OIP.3Tgoo63UxtPTz3g_BQzWVAHaE8&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Galleta Redonda", "Galletas Redonda con pasas", 500.0F, 50, "https://tse4.mm.bing.net/th?id=OIP.r1_npQDqM3xaxtmDZHwVBQHaEK&pid=Api&P=0&h=180"));
        insertProduct(db, new Product("Pan Tajado", "Pan Tajado blanco", 2000.0F, 60, "https://tse2.mm.bing.net/th?id=OIP.qF7D-NAh3ctAUXIoUXDfxwHaFj&pid=Api&P=0&h=180"));
    }

    private void insertProduct(SQLiteDatabase db, Product product) {
        ContentValues values = new ContentValues();
        values.put("nombre", product.getNombre());
        values.put("descripcion", product.getDescripcion());
        values.put("precio", product.getPrecio());
        values.put("cantidad_stock", product.getCantidad());
        values.put("imagen", product.getImage());
        db.insert(TABLE_PRODUCTS, null, values);
    }
}
