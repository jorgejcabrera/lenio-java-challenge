package com.leniolabs.challenge.controller;

import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.calculator.factory.FeeCalculatorFactory;
import com.leniolabs.challenge.controller.exception.AccountNotFoundException;
import com.leniolabs.challenge.model.Account;
import com.leniolabs.challenge.service.AccountServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lenio-challenge/account/v1")
public class AccountController {

    @Autowired
    private AccountServiceIF accountControllerService;

    @Autowired
    private FeeCalculatorFactory feeCalculatorFactory;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        String accountId = accountControllerService.createAccount(account);
        return ResponseEntity.ok(accountId);
    }

    @GetMapping(value = "/calculate-fee/{accountId}")
    public ResponseEntity<Double> calculateFee(@PathVariable String accountId) throws Exception {
        Account account = accountControllerService.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        FeeCalculatorIF calculator = feeCalculatorFactory.getCalculator(account.getAccountType());
        Double fee = calculator.calculateFee();
        return ResponseEntity.ok(fee);
    }
}
