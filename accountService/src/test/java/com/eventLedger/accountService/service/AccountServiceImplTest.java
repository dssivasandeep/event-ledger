package com.eventLedger.accountService.service;


import com.eventLedger.accountService.dto.BalanceResponse;
import com.eventLedger.accountService.entity.TransactionEntity;
import com.eventLedger.accountService.entity.TransactionType;
import com.eventLedger.accountService.repository.AccountRepository;
import com.eventLedger.accountService.repository.TransactionRepository;
import com.eventLedger.accountService.serviceImpl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class AccountServiceImplTest {

    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    private AccountServiceImpl accountService;

    @BeforeEach
    void setup() {

        transactionRepository =
                mock(TransactionRepository.class);

        accountRepository =
                mock(AccountRepository.class);

        accountService =
                new AccountServiceImpl(
                        accountRepository,
                        transactionRepository
                );
    }

    @Test
    void shouldCalculateBalanceCorrectly() {

        TransactionEntity credit =
                TransactionEntity.builder()
                        .type(TransactionType.CREDIT)
                        .amount(BigDecimal.valueOf(150))
                        .build();

        TransactionEntity debit =
                TransactionEntity.builder()
                        .type(TransactionType.DEBIT)
                        .amount(BigDecimal.valueOf(50))
                        .build();

        Mockito.when(
                        transactionRepository.findByAccountId(
                                "acct-123"))
                .thenReturn(
                        Arrays.asList(
                                credit,
                                debit));

        BalanceResponse response =
                accountService.getBalance(
                        "acct-123");

        assertEquals(
                BigDecimal.valueOf(100),
                response.getBalance());
    }
}
