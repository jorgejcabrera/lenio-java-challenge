package com.leniolabs.challenge.service;

import com.leniolabs.challenge.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccounServiceIF {

    private static List<Account> accounts = new ArrayList<>();

    @Override
    public String createAccount(Account account) {
        accounts.add(account);
        return account.getId();
    }

    @Override
    public Optional<Account> findById(String id) {
        return accounts.stream().filter(account -> account.getId().equals(id)).findFirst();
    }
}
