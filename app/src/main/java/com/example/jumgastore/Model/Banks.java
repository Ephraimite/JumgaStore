
package com.example.jumgastore.Model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Banks {

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
