package com.example.sis2.infrastructure.persistence.account;

import com.example.sis2.application.account.port.BankAccountRepository;
import com.example.sis2.domain.account.AccountCurrency;
import com.example.sis2.domain.account.BankAccount;
import com.example.sis2.mapper.account.BankAccountMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@Import({
        BankAccountJpaRepositoryAdapter.class,
        BankAccountMapper.class
})
class BankAccountRepositoryIntegrationTest {

    @Autowired
    private BankAccountRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldPersistAndLoadBankAccount() {
        BankAccount account = BankAccount.open(
                "Integration Test",
                new BigDecimal("1234.56"),
                AccountCurrency.KZT
        );

        BankAccount saved = repository.save(account);
        UUID savedId = saved.getId();

        entityManager.flush();
        entityManager.clear();

        Optional<BankAccount> result = repository.findById(savedId);

        assertThat(result).isPresent();

        BankAccount loaded = result.orElseThrow();

        assertThat(loaded.getId()).isEqualTo(savedId);
        assertThat(loaded.getAccountHolder())
                .isEqualTo("Integration Test");
        assertThat(loaded.getBalance())
                .isEqualByComparingTo("1234.56");
        assertThat(loaded.getCurrency())
                .isEqualTo(AccountCurrency.KZT);
    }
}