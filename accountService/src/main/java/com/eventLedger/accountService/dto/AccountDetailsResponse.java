package com.eventLedger.accountService.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AccountDetailsResponse {

    private String accountId;

    private BigDecimal balance;

    private List<TransactionResponse> transactions;
}