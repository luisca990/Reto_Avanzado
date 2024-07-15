package com.example.panappetit.Models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.VentaDao;
import java.util.ArrayList;
import java.util.List;

public class Venta implements Parcelable {

    private int id;
    private Integer userID;
    private Float monto_total;
    private List<Product> listProduct = new ArrayList<>();

    public Venta(int userID, Float monto_total) {
        this.userID = userID;
        this.monto_total = monto_total;
    }
    public Venta(int id, int userID, Float monto_total){
        this.id = id;
        this.userID = userID;
        this.monto_total = monto_total;
    }

    public boolean validateFieldsVenta() {
        return userID != null && monto_total != null;
    }

    public int getUserID() {
        return userID;
    }

    public Float getMontoTotal() {
        return monto_total;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<Product> getListProduct() {
        return listProduct;
    }
    public void setListProduct(List<Product> products) {
        listProduct = products;
    }

    //Metodos de consumos SQlite
    public int insertVenta(VentaDao dao, Venta venta, int pedidoId){return (int) dao.insertVenta(venta, pedidoId);}
    public static Boolean deleteDetallePedido(VentaDao dao, int idProduct, int pedidoId){return dao.deleteDetallePedido(pedidoId, idProduct);}
    public static List<Venta> getAllVentas(VentaDao dao, int idUser){return dao.getVentasByUsuarioId(idUser);}

    // Constructor para Parcel
    protected Venta(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            userID = null;
        } else {
            userID = in.readInt();
        }
        if (in.readByte() == 0) {
            monto_total = null;
        } else {
            monto_total = in.readFloat();
        }
        in.readList(listProduct, Product.class.getClassLoader());
    }

    public static final Creator<Venta> CREATOR = new Creator<Venta>() {
        @Override
        public Venta createFromParcel(Parcel in) {
            return new Venta(in);
        }

        @Override
        public Venta[] newArray(int size) {
            return new Venta[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parce, int flags) {
        parce.writeInt(id);
        if (userID == null) {
            parce.writeByte((byte) 0);
        } else {
            parce.writeByte((byte) 1);
            parce.writeInt(userID);
        }
        if (monto_total == null) {
            parce.writeByte((byte) 0);
        } else {
            parce.writeByte((byte) 1);
            parce.writeFloat(monto_total);
        }
        parce.writeList(listProduct);
    }
}