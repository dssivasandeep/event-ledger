package com.eventLedger.accountService.repository;


import com.eventLedger.accountService.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByAccountId(String accountId);

    boolean existsByEventId(String eventId);

    List<TransactionEntity> findByAccountIdOrderByEventTimestampDesc(
            String accountId);
}
