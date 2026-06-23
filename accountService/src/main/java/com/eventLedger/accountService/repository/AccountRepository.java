package com.eventLedger.accountService.repository;


import com.eventLedger.accountService.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<AccountEntity, String> {
}
