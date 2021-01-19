package com.example.jumgastore.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {

    private String pid;
    private String pname;
    private String price;
    private String discount;
    private String quantity;
    private String cartItemID;

    public Cart(String pid, String pname, String price, String discount, String quantity, String cartItemID) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.cartItemID = cartItemID;
    }

    public Cart() {
    }

    protected Cart(Parcel in) {
        pid = in.readString();
        pname = in.readString();
        price = in.readString();
        discount = in.readString();
        quantity = in.readString();
        cartItemID = in.readString();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(String cartItemID) {
        this.cartItemID = cartItemID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pid);
        dest.writeString(pname);
        dest.writeString(price);
        dest.writeString(discount);
        dest.writeString(quantity);
        dest.writeString(cartItemID);
    }
}
