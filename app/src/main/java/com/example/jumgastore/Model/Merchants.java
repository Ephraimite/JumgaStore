package com.example.jumgastore.Model;

public class Merchants {

    String MerchantName, isPaidUser, SubAccountID, phone, shopName, email, password;



    public Merchants(String merchantName, String isPaidUser, String subAccountID, String phone, String shopName, String email, String password) {
        MerchantName = merchantName;
        this.isPaidUser = isPaidUser;
        this.SubAccountID = subAccountID;
        this.phone = phone;
        this.shopName = shopName;
        this.email = email;
        this.password = password;
    }

    public Merchants() {
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getIsPaidUser() {
        return isPaidUser;
    }

    public void setIsPaidUser(String isPaidUser) {
        this.isPaidUser = isPaidUser;
    }

    public String getSubAccountID() {
        return SubAccountID;
    }

    public void setSubAccountID(String subAccountID) {
        this.SubAccountID = subAccountID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
