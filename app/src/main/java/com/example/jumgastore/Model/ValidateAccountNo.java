
package com.example.jumgastore.Model;


import com.google.gson.annotations.SerializedName;

public class ValidateAccountNo {

    @SerializedName("data")
    private ValidateAccountData mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public ValidateAccountData getData() {
        return mData;
    }

    public void setData(ValidateAccountData data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
