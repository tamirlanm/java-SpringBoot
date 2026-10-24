package com.example.sis2.infrastructure.persistence.account;

import com.example.sis2.domain.account.AccountCurrency;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
public class BankAccountEntity {

    @Id
    private UUID id;

    @Column(name = "account_holder", nullable = false, length = 120)
    private String accountHolder;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private AccountCurrency currency;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected BankAccountEntity(){
    }

    public BankAccountEntity(
            UUID id,
            String accountHolder,
            BigDecimal balance,
            AccountCurrency currency,
            Instant createdAt
    ) {
        this.id = id;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountCurrency getCurrency() {
        return currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}