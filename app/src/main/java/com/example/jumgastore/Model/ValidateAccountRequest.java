package com.example.jumgastore.Model;

public class ValidateAccountRequest {
    String account_number;
    String account_bank;

    public ValidateAccountRequest(String account_number, String account_bank) {
        this.account_number = account_number;
        this.account_bank = account_bank;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }
}
