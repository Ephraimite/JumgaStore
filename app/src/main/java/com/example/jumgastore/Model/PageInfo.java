
package com.example.jumgastore.Model;

import com.google.gson.annotations.SerializedName;

public class PageInfo {

    @SerializedName("current_page")
    private Long mCurrentPage;
    @SerializedName("total")
    private Long mTotal;
    @SerializedName("total_pages")
    private Long mTotalPages;

    public Long getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(Long currentPage) {
        mCurrentPage = currentPage;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

}
