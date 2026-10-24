package com.example.sis2.application.account;

import com.example.sis2.application.account.port.BankAccountRepository;
import com.example.sis2.domain.account.AccountCurrency;
import com.example.sis2.domain.account.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BankAccountService {

    private final BankAccountRepository repository;

    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public BankAccount createAccount(
            String accountHolder,
            BigDecimal initialBalance,
            AccountCurrency currency
    ) {
        BankAccount account = BankAccount.open(
                accountHolder,
                initialBalance,
                currency
        );

        return repository.save(account);
    }

    @Transactional(readOnly = true)
    public BankAccount getAccount(UUID id) {
        return repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    @Transactional
    public BankAccount deposit(UUID id, BigDecimal amount) {
        BankAccount account = getAccount(id);

        account.deposit(amount);

        return repository.save(account);
    }

    @Transactional
    public BankAccount withdraw(UUID id, BigDecimal amount) {
        BankAccount account = getAccount(id);

        account.withdraw(amount);

        return repository.save(account);
    }

    @Transactional
    public BankAccount createAccount(BankAccount account) {
        return repository.save(account);
    }
}