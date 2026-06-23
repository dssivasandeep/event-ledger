package com.eventLedger.accountService.dto;

import com.eventLedger.accountService.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionRequest {

    private String eventId;

    private TransactionType type;

    private BigDecimal amount;

    private Instant eventTimestamp;
}
