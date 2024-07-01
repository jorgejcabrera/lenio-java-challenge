package com.leniolabs.challenge.controller.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountId) {
        super("Account not found with ID: " + accountId);
    }
}