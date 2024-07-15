package com.example.panappetit.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.ProductDao;

import java.util.List;

public class Product implements Parcelable {

    private int id;
    private final String nombre;
    private final String descripcion;
    private final Float precio;
    private final Integer cantidad;
    private final String image;
    private Boolean selected = true;
    private Integer productCantidad;

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
    public int getProductCantidad() {
        return productCantidad;
    }
    public void setProductCantidad(int productCantidad) {
        this.productCantidad = productCantidad;
    }
    public Boolean getSelected() {
        return selected;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    //Metodos de consumos SQlite
    public int insertProduct(ProductDao dao, Product product){return (int) dao.insertProduct(product);}
    public int updateProduct(ProductDao dao, Product product){return (int) dao.updateProduct(product);}
    public Boolean deleteProduct(ProductDao dao){return dao.deleteProduct(id);}
    public static List<Product> getListProduct(ProductDao dao){
        return dao.getListProducts();
    }

    // Parceo del modelo
    protected Product(Parcel in){
        id = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
        cantidad = in.readInt();
        precio = in.readFloat();
        image = in.readString();
        productCantidad = in.readInt();
        selected = in.readBoolean();
    }
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeInt(cantidad);
        parcel.writeFloat(precio);
        parcel.writeString(image);
        parcel.writeInt(productCantidad);
        parcel.writeBoolean(selected);
    }
}