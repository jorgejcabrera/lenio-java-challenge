package com.leniolabs.challenge.calculator;

import com.leniolabs.challenge.custom.AccountType;
import org.springframework.stereotype.Component;

@AccountType(value = "corporate")
@Component
public class CorporateAccountFeeCalculator implements FeeCalculatorIF{

    @Override
    public Double calculateFee() {
        return 0.05;
    }
}
