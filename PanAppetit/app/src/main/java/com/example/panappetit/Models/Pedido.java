package com.example.panappetit.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.panappetit.DataAccess.DatabaseSQLite.Daos.PedidoDao;

import java.util.ArrayList;
import java.util.List;

public class Pedido  implements Parcelable {

    private int id;
    private Integer userID;
    private String date;
    private Float monto_total;
    private Product product;
    private final List<Product> listProduct = new ArrayList<>();

    public Pedido(int userID, String date, Float monto_total, Product product) {
        this.userID = userID;
        this.date = date;
        this.monto_total = monto_total;
        this.product = product;
    }
    public Pedido(int id, int userID, String date, Float monto_total){
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.monto_total = monto_total;
    }
    public Pedido(){}

    public boolean validateFieldsPedidos() {
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
    public void setId(int id) {
        this.id = id;
    }
    public List<Product> getListProduct() {
        return listProduct;
    }
    public void settListProduct(Product product) {
        listProduct.add(product);
    }

    //Metodos de consumos SQlite
    public int insertPedido(PedidoDao dao, Pedido pedido){return (int) dao.insertPedido(pedido);}
    public int updatePedido(PedidoDao dao, Pedido pedido){return (int) dao.updatePedido(pedido);}
    public static Pedido getLastPedidoByUserId(PedidoDao dao, int userId){return dao.getLastPedidoByUserId(userId);}

    // Constructor para Parcel
    protected Pedido(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            userID = null;
        } else {
            userID = in.readInt();
        }
        date = in.readString();
        if (in.readByte() == 0) {
            monto_total = null;
        } else {
            monto_total = in.readFloat();
        }
        product = in.readParcelable(Product.class.getClassLoader());
        in.readList(listProduct, Product.class.getClassLoader());
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
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
        parce.writeString(date);
        if (monto_total == null) {
            parce.writeByte((byte) 0);
        } else {
            parce.writeByte((byte) 1);
            parce.writeFloat(monto_total);
        }
        parce.writeParcelable(product, flags);
        parce.writeList(listProduct);
    }
}