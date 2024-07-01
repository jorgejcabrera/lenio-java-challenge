package com.leniolabs.challenge.service;

import com.leniolabs.challenge.model.Account;

import java.util.Optional;

public interface AccountServiceIF {

    public String createAccount(Account account);

    Optional<Account> findById(String id);
}
