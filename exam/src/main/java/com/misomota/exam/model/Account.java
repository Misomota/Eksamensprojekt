package com.misomota.exam.model;

public class Account {
    private String username;
    private String password;
    private int accountID;

    public Account(String username, String password, int accountID) {
        this.username = username;
        this.password = password;
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
