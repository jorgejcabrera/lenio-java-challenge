package com.leniolabs.challenge.calculator.factory;

import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.custom.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FeeCalculatorFactory {
    private final Map<String, FeeCalculatorIF> calculators;

    @Autowired
    public FeeCalculatorFactory(List<FeeCalculatorIF> calculatorList) {
        this.calculators = calculatorList.stream().collect(
                Collectors.toMap(calculator -> calculator.getClass().getAnnotation(AccountType.class).value(), calculator -> calculator)
        );
    }

    public FeeCalculatorIF getCalculator(String type) {
        return calculators.get(type);
    }
}
