
package com.example.jumgastore.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetAllSubAccountResponse {

    @SerializedName("data")
    private List<SubAccounts> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("meta")
    private Meta mMeta;
    @SerializedName("status")
    private String mStatus;

    public List<SubAccounts> getData() {
        return mData;
    }

    public void setData(List<SubAccounts> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
