package com.eventLedger.accountService.service;

import com.eventLedger.accountService.dto.AccountDetailsResponse;
import com.eventLedger.accountService.dto.BalanceResponse;
import com.eventLedger.accountService.dto.TransactionRequest;

public interface AccountService {

    void applyTransaction(String accountId,
                          TransactionRequest request);

    BalanceResponse getBalance(String accountId);

    AccountDetailsResponse getAccountDetails(
            String accountId);
}