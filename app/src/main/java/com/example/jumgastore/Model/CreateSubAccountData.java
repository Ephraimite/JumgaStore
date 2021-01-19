
package com.example.jumgastore.Model;

import com.google.gson.annotations.SerializedName;

public class CreateSubAccountData {

    @SerializedName("account_bank")
    private String mAccountBank;
    @SerializedName("account_number")
    private String mAccountNumber;
    @SerializedName("bank_name")
    private String mBankName;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("id")
    private Long mId;
    @SerializedName("split_type")
    private String mSplitType;
    @SerializedName("split_value")
    private Double mSplitValue;
    @SerializedName("subaccount_id")
    private String mSubaccountId;

    public String getAccountBank() {
        return mAccountBank;
    }

    public void setAccountBank(String accountBank) {
        mAccountBank = accountBank;
    }

    public String getAccountNumber() {
        return mAccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        mAccountNumber = accountNumber;
    }

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        mBankName = bankName;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getSplitType() {
        return mSplitType;
    }

    public void setSplitType(String splitType) {
        mSplitType = splitType;
    }

    public Double getSplitValue() {
        return mSplitValue;
    }

    public void setSplitValue(Double splitValue) {
        mSplitValue = splitValue;
    }

    public String getSubaccountId() {
        return mSubaccountId;
    }

    public void setSubaccountId(String subaccountId) {
        mSubaccountId = subaccountId;
    }

}
