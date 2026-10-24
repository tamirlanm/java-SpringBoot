package com.example.sis2.web.account.dto;

import com.example.sis2.domain.account.AccountCurrency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String accountHolder,
        BigDecimal balance,
        AccountCurrency currency,
        Instant createdAt
) {
}