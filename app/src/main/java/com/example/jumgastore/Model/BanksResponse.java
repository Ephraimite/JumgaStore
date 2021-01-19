
package com.example.jumgastore.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class BanksResponse {

    @SerializedName("data")
    private List<Banks> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public void setData(List<Banks> data){
        this.data = data;
    }

    public List<Banks> getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
