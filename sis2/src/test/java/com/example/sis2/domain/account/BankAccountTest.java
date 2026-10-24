package com.example.sis2.domain.account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankAccountTest {

    @Test
    void shouldDepositMoney() {
        BankAccount account = BankAccount.open(
                "Tamirlan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT
        );

        account.deposit(new BigDecimal("500.00"));

        assertThat(account.getBalance())
                .isEqualByComparingTo("1500.00");
    }

    @Test
    void shouldWithdrawMoney() {
        BankAccount account = BankAccount.open(
                "Tamirlan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT
        );

        account.withdraw(new BigDecimal("300.00"));

        assertThat(account.getBalance())
                .isEqualByComparingTo("700.00");
    }

    @Test
    void shouldNotAllowWithdrawalWhenFundsAreInsufficient() {
        BankAccount account = BankAccount.open(
                "Tamirlan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT
        );

        assertThatThrownBy(
                () -> account.withdraw(new BigDecimal("1500.00"))
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Insufficient funds");

        assertThat(account.getBalance())
                .isEqualByComparingTo("1000.00");
    }

    @Test
    void shouldNotAllowZeroDeposit() {
        BankAccount account = BankAccount.open(
                "Tamirlan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT
        );

        assertThatThrownBy(
                () -> account.deposit(BigDecimal.ZERO)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be greater than zero");
    }

    @Test
    void shouldNotAllowNegativeDeposit() {
        BankAccount account = BankAccount.open(
                "Tamirlan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT
        );

        assertThatThrownBy(
                () -> account.deposit(new BigDecimal("-100.00"))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be greater than zero");
    }

    @Test
    void shouldNotAllowNegativeInitialBalance() {
        assertThatThrownBy(
                () -> BankAccount.open(
                        "Tamirlan",
                        new BigDecimal("-100.00"),
                        AccountCurrency.KZT
                )
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Initial balance cannot be negative");
    }
}