package com.example.sis2.application.account;

import com.example.sis2.application.account.port.BankAccountRepository;
import com.example.sis2.domain.account.AccountCurrency;
import com.example.sis2.domain.account.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository repository;

    @InjectMocks
    private BankAccountService service;

    @Test
    void shouldCreateAccount() {
        when(repository.save(any(BankAccount.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BankAccount account = service.createAccount(
                "Ramazan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT
        );

        assertThat(account.getId()).isNotNull();
        assertThat(account.getAccountHolder()).isEqualTo("Ramazan");
        assertThat(account.getBalance())
                .isEqualByComparingTo("1000.00");
        assertThat(account.getCurrency()).isEqualTo(AccountCurrency.KZT);

        verify(repository).save(any(BankAccount.class));
    }

    @Test
    void shouldReturnExistingAccount() {
        BankAccount account = createTestAccount();

        when(repository.findById(account.getId()))
                .thenReturn(Optional.of(account));

        BankAccount result = service.getAccount(account.getId());

        assertThat(result.getId()).isEqualTo(account.getId());
        assertThat(result.getBalance())
                .isEqualByComparingTo("1000.00");
    }

    @Test
    void shouldDepositMoney() {
        BankAccount account = createTestAccount();

        when(repository.findById(account.getId()))
                .thenReturn(Optional.of(account));

        when(repository.save(any(BankAccount.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BankAccount result = service.deposit(
                account.getId(),
                new BigDecimal("250.00")
        );

        assertThat(result.getBalance())
                .isEqualByComparingTo("1250.00");

        verify(repository).save(account);
    }

    @Test
    void shouldWithdrawMoney() {
        BankAccount account = createTestAccount();

        when(repository.findById(account.getId()))
                .thenReturn(Optional.of(account));

        when(repository.save(any(BankAccount.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BankAccount result = service.withdraw(
                account.getId(),
                new BigDecimal("300.00")
        );

        assertThat(result.getBalance())
                .isEqualByComparingTo("700.00");

        verify(repository).save(account);
    }

    @Test
    void shouldThrowWhenAccountDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getAccount(id))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Bank account not found: " + id);
    }

    @Test
    void shouldNotSaveAccountWhenWithdrawalFails() {
        BankAccount account = createTestAccount();

        when(repository.findById(account.getId()))
                .thenReturn(Optional.of(account));

        assertThatThrownBy(() -> service.withdraw(
                account.getId(),
                new BigDecimal("1500.00")
        ))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Insufficient funds");

        verify(repository, never()).save(any(BankAccount.class));

        assertThat(account.getBalance())
                .isEqualByComparingTo("1000.00");
    }

    private BankAccount createTestAccount() {
        return BankAccount.open(
                "Ramazan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT
        );
    }
}