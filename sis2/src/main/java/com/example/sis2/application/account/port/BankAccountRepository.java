package com.example.sis2.application.account.port;

import com.example.sis2.domain.account.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {

    BankAccount save(BankAccount account);

    Optional<BankAccount> findById(UUID id);

    List<BankAccount> findAll();
}