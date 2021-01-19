package com.example.jumgastore.Model;

public class Products {

    private String productname, description, price, Image, category, pid, date, time, sid;

    public Products(String productname, String description, String price, String image, String category, String pid, String date, String time, String sid) {
        this.productname = productname;
        this.description = description;
        this.price = price;
        Image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.sid = sid;
    }

    public Products() {
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
