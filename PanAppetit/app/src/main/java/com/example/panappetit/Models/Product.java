package com.example.panappetit.Models;

public class Product {

    private int id;
    private final String nombre;
    private final String descripcion;
    private final Float precio;
    private final Integer cantidad;
    private final String image;

    public Product(String nombre, String descripcion, Float precio, Integer cantidad, String image) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.image = image;
    }

    public boolean validateFieldsProduct() {
        return nombre != null && !nombre.isEmpty()
                && descripcion != null && !descripcion.isEmpty()
                && precio != null && cantidad != null
                && image != null && !image.isEmpty();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getImage() {
        return image;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}