package com.example.jumgastore.Model;

public class Users {

    private  String name, password, phone, email, image;

    public Users(String name, String password, String phone, String email, String image) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public Users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
