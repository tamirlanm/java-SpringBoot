package com.example.sis2.web.account.dto;

import com.example.sis2.domain.account.AccountCurrency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "Account holder cannot be blank")
        @Size(max = 120, message = "Account holder must not exceed 120 characters")
        String accountHolder,

        @NotNull(message = "Initial balance is required")
        @DecimalMin(value = "0.00", message = "Initial balance cannot be negative")
        @Digits(integer = 17, fraction = 2)
        BigDecimal initialBalance,

        @NotNull(message = "Currency is required")
        AccountCurrency currency
) {
}