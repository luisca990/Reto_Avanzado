package com.example.panappetit.Models;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.PedidoDao;

import java.util.List;

public class Pedido {

    private int id;
    private final Integer userID;
    private final String date;
    private final Float monto_total;
    private Product product;
    private List<Product> listProduct;
    private Integer productCantidad;

    public Pedido(int userID, String date, Float monto_total, Product product, Integer productCantidad) {
        this.userID = userID;
        this.date = date;
        this.monto_total = monto_total;
        this.product = product;
        this.productCantidad = productCantidad;
    }

    public boolean validateFieldsProduct() {
        return userID != null && monto_total != null
                && date != null && !date.isEmpty() && product!= null;
    }

    public int getUserID() {
        return userID;
    }
    public String getDate() {
        return date;
    }

    public Float getMontoTotal() {
        return monto_total;
    }

    public Product getProduct() {
        return product;
    }
    public int getId() {
        return id;
    }
    public int getProductCantidad() {
        return productCantidad;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Metodos de consumos SQlite
    public int insertPedido(PedidoDao dao, Pedido pedido){return (int) dao.insertPedido(pedido);}
    public int updatePedido(PedidoDao dao, Pedido pedido){return (int) dao.updatePedido(pedido);}
    //public static List<Venta> getListVenta(ProductDao dao){return dao.getListProducts();}

}