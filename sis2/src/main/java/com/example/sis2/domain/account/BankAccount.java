package com.example.sis2.domain.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class BankAccount {
    private final UUID id;
    private final String accountHolder;
    private BigDecimal balance;
    private final AccountCurrency currency;
    private final Instant createdAt;

    private BankAccount(
            UUID id,
            String accountHolder,
            BigDecimal balance,
            AccountCurrency currency,
            Instant createdAt
    ) {
        if(id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if(accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException("accountHolder cannot be null");
        }
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        if(currency == null) {
            throw new IllegalArgumentException("currency cannot be null");
        }
        if(createdAt == null) {
            throw new IllegalArgumentException("Creation date cannot be null");
        }

        this.id = id;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public static BankAccount open(
            String accountHolder,
            BigDecimal initialBalance,
            AccountCurrency currency
    ){
        return new BankAccount(
                UUID.randomUUID(),
                accountHolder,
                initialBalance,
                currency,
                Instant.now()
        );
    }

    public static BankAccount restore(
            UUID id,
            String accountHolder,
            BigDecimal balance,
            AccountCurrency currency,
            Instant createdAt
    ){
        return new BankAccount(
                id,
                accountHolder,
                balance,
                currency,
                createdAt
        );
    }

    public void deposit(BigDecimal amount){
        validatePositiveAmount(amount);
        balance = balance.add(amount);
    }
    public void withdraw(BigDecimal amount){
        validatePositiveAmount(amount);
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        balance = balance.subtract(amount);
    }

    private void validatePositiveAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }
    }

    public UUID getId(){return id;}
    public String getAccountHolder(){return accountHolder;}
    public BigDecimal getBalance(){return balance;}
    public AccountCurrency getCurrency(){return currency;}
    public Instant getCreatedAt(){return createdAt;}
}