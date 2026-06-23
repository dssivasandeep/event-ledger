package com.eventLedger.accountService.serviceImpl;

import com.eventLedger.accountService.dto.AccountDetailsResponse;
import com.eventLedger.accountService.dto.BalanceResponse;
import com.eventLedger.accountService.dto.TransactionRequest;
import com.eventLedger.accountService.dto.TransactionResponse;
import com.eventLedger.accountService.entity.AccountEntity;
import com.eventLedger.accountService.entity.TransactionEntity;
import com.eventLedger.accountService.entity.TransactionType;
import com.eventLedger.accountService.repository.AccountRepository;
import com.eventLedger.accountService.repository.TransactionRepository;
import com.eventLedger.accountService.service.AccountService;
import com.eventLedger.accountService.trace.TraceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void applyTransaction(String accountId,
                                 TransactionRequest request) {
        log.info(
                "Applying transaction event={} traceId={}",
                request.getEventId(),
                TraceContext.getTraceId()
        );
        if (transactionRepository.existsByEventId(request.getEventId())) {
            return;
        }

        accountRepository.findById(accountId)
                .orElseGet(() ->
                        accountRepository.save(
                                AccountEntity.builder()
                                        .accountId(accountId)
                                        .build()
                        ));

        TransactionEntity transaction = TransactionEntity.builder()
                .eventId(request.getEventId())
                .accountId(accountId)
                .type(request.getType())
                .amount(request.getAmount())
                .eventTimestamp(request.getEventTimestamp())
                .build();

        transactionRepository.save(transaction);
    }

    @Override
    public BalanceResponse getBalance(String accountId) {

        List<TransactionEntity> transactions =
                transactionRepository.findByAccountId(accountId);

        BigDecimal balance = transactions.stream()
                .map(tx -> tx.getType() == TransactionType.CREDIT
                        ? tx.getAmount()
                        : tx.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BalanceResponse(accountId, balance);
    }

    @Override
    public AccountDetailsResponse getAccountDetails(
            String accountId) {

        List<TransactionEntity> transactions =
                transactionRepository
                        .findByAccountIdOrderByEventTimestampDesc(
                                accountId);

        BigDecimal balance = transactions.stream()
                .map(tx ->
                        tx.getType() == TransactionType.CREDIT
                                ? tx.getAmount()
                                : tx.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<TransactionResponse> transactionResponses =
                transactions.stream()
                        .map(tx ->
                                TransactionResponse.builder()
                                        .eventId(tx.getEventId())
                                        .type(tx.getType())
                                        .amount(tx.getAmount())
                                        .eventTimestamp(
                                                tx.getEventTimestamp())
                                        .build())
                        .collect(Collectors.toList());

        return AccountDetailsResponse.builder()
                .accountId(accountId)
                .balance(balance)
                .transactions(transactionResponses)
                .build();
    }

}
