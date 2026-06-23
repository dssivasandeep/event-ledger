package com.eventLedger.accountService.controller;

import com.eventLedger.accountService.dto.AccountDetailsResponse;
import com.eventLedger.accountService.dto.BalanceResponse;
import com.eventLedger.accountService.dto.TransactionRequest;
import com.eventLedger.accountService.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountId}/transactions")
    public void applyTransaction(
            @PathVariable String accountId,
            @RequestBody TransactionRequest request) {

        accountService.applyTransaction(accountId, request);
    }

    @GetMapping("/{accountId}/balance")
    public BalanceResponse getBalance(
            @PathVariable String accountId) {

        return accountService.getBalance(accountId);
    }

    @GetMapping("/{accountId}")
    public AccountDetailsResponse getAccountDetails(
            @PathVariable String accountId) {

        return accountService.getAccountDetails(
                accountId);
    }
}
