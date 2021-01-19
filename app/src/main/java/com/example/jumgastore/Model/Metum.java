
package com.example.jumgastore.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Metum {

    @SerializedName("meta_name")
    private String mMetaName;
    @SerializedName("meta_value")
    private String mMetaValue;

    public String getMetaName() {
        return mMetaName;
    }

    public void setMetaName(String metaName) {
        mMetaName = metaName;
    }

    public String getMetaValue() {
        return mMetaValue;
    }

    public void setMetaValue(String metaValue) {
        mMetaValue = metaValue;
    }

}
