package com.eventLedger.accountService.serviceImpl;

import com.eventLedger.accountService.dto.BalanceResponse;
import com.eventLedger.accountService.dto.TransactionRequest;
import com.eventLedger.accountService.entity.AccountEntity;
import com.eventLedger.accountService.entity.TransactionEntity;
import com.eventLedger.accountService.entity.TransactionType;
import com.eventLedger.accountService.repository.AccountRepository;
import com.eventLedger.accountService.repository.TransactionRepository;
import com.eventLedger.accountService.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void applyTransaction(String accountId,
                                 TransactionRequest request) {

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
}
