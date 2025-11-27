package com.misomota.exam.model;

public class Account {
    private String username;
    private String accountPassword;
    private int accountID;

    public Account(String username, String accountPassword, int accountID) {
        this.username = username;
        this.accountPassword = accountPassword;
        this.accountID = accountID;
    }

    public Account() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
