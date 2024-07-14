package com.example.panappetit.Utils;

import com.example.panappetit.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static class Tag {
        public static final String LOGIN = "Login";
        public static final String REGISTER = "Register";
        public static final String ADMIN = "admin@gmail.com";
        public static final String USER = "user";
    }

    //  Nombres Tablas
    public static final String TABLE_PRODUCTS = "productos";
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_PEDIDOS = "pedidos";
    public static final String TABLE_VENTA = "venta";
    public static final String TABLE_DETALLES = "detalles_pedido";
    public static final String TABLE_DETALLES_VENTA = "detalles_venta";

    // Query Tablas
    public static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "nombre TEXT NOT NULL,"
            + "descripcion TEXT,"
            + "precio REAL NOT NULL,"
            + "cantidad_stock INTEGER NOT NULL,"
            + "imagen TEXT"
            + ")";
    public static final String CREATE_TABLE_USUARIOS = "CREATE TABLE " + TABLE_USUARIOS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "correo TEXT NOT NULL UNIQUE"
            + ")";
    public static final String CREATE_TABLE_PEDIDOS = "CREATE TABLE " + TABLE_PEDIDOS + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "usuario_id INTEGER NOT NULL,"
            + "fecha_pedido TEXT NOT NULL,"
            + "monto_total REAL NOT NULL,"
            + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id)"
            + ")";
    public static final String CREATE_TABLE_VENTA = "CREATE TABLE " + TABLE_VENTA + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "usuario_id INTEGER NOT NULL,"
            + "monto_total REAL NOT NULL,"
            + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id)"
            + ")";
    public static final String CREATE_TABLE_DETALLES = "CREATE TABLE " + TABLE_DETALLES + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "pedido_id INTEGER NOT NULL,"
            + "producto_id INTEGER NOT NULL,"
            + "cantidad INTEGER NOT NULL,"
            + "precio REAL NOT NULL,"
            + "FOREIGN KEY (pedido_id) REFERENCES pedidos(id),"
            + "FOREIGN KEY (producto_id) REFERENCES productos(id)"
            + ")";
    public static final String CREATE_TABLE_DETALLES_VENTA = "CREATE TABLE " + TABLE_DETALLES_VENTA + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "venta_id INTEGER NOT NULL,"
            + "producto_id INTEGER NOT NULL,"
            + "cantidad INTEGER NOT NULL,"
            + "FOREIGN KEY (venta_id) REFERENCES venta(id),"
            + "FOREIGN KEY (producto_id) REFERENCES productos(id)"
            + ")";
}
