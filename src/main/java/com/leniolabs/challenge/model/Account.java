package com.leniolabs.challenge.model;

public class Account {

    private String id;
    private String name;
    private String lastName;
    private String accountType;
    private Double balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Account (String id, String name, String lastName, String accountType, Double balance) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.accountType = accountType;
        this.balance = balance;
    }
}
