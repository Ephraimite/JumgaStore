package com.example.jumgastore.Model;

import com.google.gson.annotations.SerializedName;

public class CreateSubAccountRequest {

    @SerializedName("account_bank")
    private String mAccountBank;
    @SerializedName("account_number")
    private String mAccountNumber;
    @SerializedName("business_contact")
    private String mBusinessContact;
    @SerializedName("business_contact_mobile")
    private String mBusinessContactMobile;
    @SerializedName("business_email")
    private String mBusinessEmail;
    @SerializedName("business_mobile")
    private String mBusinessMobile;
    @SerializedName("business_name")
    private String mBusinessName;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("split_type")
    private String mSplitType;
    @SerializedName("split_value")
    private Double mSplitValue;

    public CreateSubAccountRequest(String mAccountBank, String mAccountNumber, String mBusinessContact, String mBusinessContactMobile, String mBusinessEmail, String mBusinessMobile, String mBusinessName, String mCountry, String mSplitType, Double mSplitValue) {
        this.mAccountBank = mAccountBank;
        this.mAccountNumber = mAccountNumber;
        this.mBusinessContact = mBusinessContact;
        this.mBusinessContactMobile = mBusinessContactMobile;
        this.mBusinessEmail = mBusinessEmail;
        this.mBusinessMobile = mBusinessMobile;
        this.mBusinessName = mBusinessName;
        this.mCountry = mCountry;
        this.mSplitType = mSplitType;
        this.mSplitValue = mSplitValue;
    }

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

    public String getBusinessContact() {
        return mBusinessContact;
    }

    public void setBusinessContact(String businessContact) {
        mBusinessContact = businessContact;
    }

    public String getBusinessContactMobile() {
        return mBusinessContactMobile;
    }

    public void setBusinessContactMobile(String businessContactMobile) {
        mBusinessContactMobile = businessContactMobile;
    }

    public String getBusinessEmail() {
        return mBusinessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        mBusinessEmail = businessEmail;
    }

    public String getBusinessMobile() {
        return mBusinessMobile;
    }

    public void setBusinessMobile(String businessMobile) {
        mBusinessMobile = businessMobile;
    }

    public String getBusinessName() {
        return mBusinessName;
    }

    public void setBusinessName(String businessName) {
        mBusinessName = businessName;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
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

}
