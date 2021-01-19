
package com.example.jumgastore.Model;


import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("page_info")
    private PageInfo mPageInfo;

    public PageInfo getPageInfo() {
        return mPageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        mPageInfo = pageInfo;
    }

}
