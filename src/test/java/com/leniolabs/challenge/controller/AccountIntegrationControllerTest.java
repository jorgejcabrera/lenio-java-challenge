package com.leniolabs.challenge.controller;

import com.leniolabs.challenge.model.Account;
import com.leniolabs.challenge.service.AccountServiceIF;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountIntegrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceIF accountControllerService;

    public static Stream<Arguments> accountDataProvider() {
        return Stream.of(
                Arguments.of("1", 0.05, "corporate", new Account("1", "John", "Doe", "corporate", 10000.0)),
                Arguments.of("2", 0.01, "personal", new Account("2", "Jane", "Doe", "personal", 10000.0))
        );
    }

    @ParameterizedTest(name = "{index} => Test for account type {2} expecting fee {1}")
    @MethodSource("accountDataProvider")
    public void testCalculateFee(String accountId, double expectedFee, String accountType, Account account) throws Exception {
        // given
        given(accountControllerService.findById(accountId)).willReturn(Optional.of(account));

        // when
        mockMvc.perform(get("/lenio-challenge/account/v1/calculate-fee/" + accountId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(Double.toString(expectedFee)));
    }
}
