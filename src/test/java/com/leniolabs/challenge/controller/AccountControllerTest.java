package com.leniolabs.challenge.controller;

import com.leniolabs.challenge.calculator.CorporateAccountFeeCalculator;
import com.leniolabs.challenge.calculator.FeeCalculatorIF;
import com.leniolabs.challenge.calculator.PersonalAccountFeeCalculator;
import com.leniolabs.challenge.calculator.factory.FeeCalculatorFactory;
import com.leniolabs.challenge.controller.exception.AccountNotFoundException;
import com.leniolabs.challenge.model.Account;
import com.leniolabs.challenge.service.AccountServiceIF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountServiceIF accountControllerService;

    @Mock
    private FeeCalculatorFactory feeCalculatorFactory;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .setControllerAdvice(new ExceptionControllerHandler())
                .build();
    }

    @ParameterizedTest(name = "{index} => Test for {0} account with expected fee {1}")
    @MethodSource("provideAccountTypesForTesting")
    public void testCalculateFeeForDifferentAccountTypes(String accountType, double expectedFee, FeeCalculatorIF calculator) throws Exception {
        // given
        String accountId = "1";
        Account account = new Account(accountId, "John", "Doe", accountType, 10000.0);

        when(accountControllerService.findById(accountId)).thenReturn(Optional.of(account));
        when(feeCalculatorFactory.getCalculator(account.getAccountType())).thenReturn(calculator);

        // when
        mockMvc.perform(get("/lenio-challenge/account/v1/calculate-fee/" + accountId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Double.toString(expectedFee)));

        // then
        verify(accountControllerService, times(1)).findById(accountId);
        verify(feeCalculatorFactory, times(1)).getCalculator(account.getAccountType());
    }

    @Test
    public void testCalculateFeeAccountNotFound() throws Exception {
        // Given
        when(accountControllerService.findById(anyString())).thenThrow(new AccountNotFoundException("1"));

        // When
        mockMvc.perform(get("/lenio-challenge/account/v1/calculate-fee/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found with ID: 1"));
    }

    public static Stream<Arguments> provideAccountTypesForTesting() {
        return Stream.of(
                Arguments.of("corporate", 0.05, new CorporateAccountFeeCalculator()),
                Arguments.of("personal", 0.01, new PersonalAccountFeeCalculator())
        );
    }
}