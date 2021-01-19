
package com.example.jumgastore.Model;


import com.google.gson.annotations.SerializedName;
public class CreateSubAccountResponse {

    @SerializedName("data")
    private CreateSubAccountData mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public CreateSubAccountData getData() {
        return mData;
    }

    public void setData(CreateSubAccountData data) {
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
