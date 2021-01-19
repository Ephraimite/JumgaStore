
package com.example.jumgastore.Model;


import com.google.gson.annotations.SerializedName;


public class ValidateAccountData {

    @SerializedName("account_name")
    private String mAccountName;
    @SerializedName("account_number")
    private String mAccountNumber;

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public String getAccountNumber() {
        return mAccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        mAccountNumber = accountNumber;
    }

}
