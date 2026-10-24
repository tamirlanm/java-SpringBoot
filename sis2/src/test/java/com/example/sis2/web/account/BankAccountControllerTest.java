package com.example.sis2.web.account;

import com.example.sis2.application.account.AccountNotFoundException;
import com.example.sis2.application.account.BankAccountService;
import com.example.sis2.domain.account.AccountCurrency;
import com.example.sis2.domain.account.BankAccount;
import com.example.sis2.mapper.account.BankAccountDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
@Import(BankAccountDtoMapper.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankAccountService service;

    @Test
    void shouldCreateAccountAndReturn201() throws Exception {
        BankAccount account = createTestAccount();

        given(service.createAccount(any(BankAccount.class)))
                .willReturn(account);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountHolder": "Aliahan",
                                  "initialBalance": 1000.00,
                                  "currency": "KZT"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(account.getId().toString()))
                .andExpect(jsonPath("$.accountHolder")
                        .value("Aliahan"))
                .andExpect(jsonPath("$.balance").value(1000.00))
                .andExpect(jsonPath("$.currency").value("KZT"));
    }

    @Test
    void shouldReturnAccountAndStatus200() throws Exception {
        BankAccount account = createTestAccount();

        given(service.getAccount(account.getId()))
                .willReturn(account);

        mockMvc.perform(get("/api/accounts/{id}", account.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(account.getId().toString()))
                .andExpect(jsonPath("$.balance").value(1000.00))
                .andExpect(jsonPath("$.currency").value("KZT"));
    }

    @Test
    void shouldReturn404WhenAccountDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        given(service.getAccount(id))
                .willThrow(new AccountNotFoundException(id));

        mockMvc.perform(get("/api/accounts/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Bank account not found: " + id));
    }

    @Test
    void shouldRejectInvalidAccountRequest() throws Exception {
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountHolder": " ",
                                  "initialBalance": -100.00,
                                  "currency": "KZT"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").isNotEmpty());

        verify(service, never())
                .createAccount(any(BankAccount.class));
    }

    @Test
    void shouldDepositMoneyAndReturn200() throws Exception {
        UUID id = UUID.randomUUID();

        BankAccount updatedAccount = BankAccount.open(
                "Aliahan",
                new BigDecimal("1250.00"),
                AccountCurrency.KZT
        );

        given(service.deposit(id, new BigDecimal("250.00")))
                .willReturn(updatedAccount);

        mockMvc.perform(post("/api/accounts/{id}/deposit", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 250.00
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1250.00));
    }

    @Test
    void shouldRejectZeroDepositBeforeCallingService() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/api/accounts/{id}/deposit", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        verify(service, never())
                .deposit(org.mockito.ArgumentMatchers.eq(id), any());
    }

    @Test
    void shouldReturn409WhenWithdrawalHasInsufficientFunds()
            throws Exception {
        UUID id = UUID.randomUUID();

        given(service.withdraw(id, new BigDecimal("1500.00")))
                .willThrow(new IllegalStateException("Insufficient funds"));

        mockMvc.perform(post("/api/accounts/{id}/withdraw", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 1500.00
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message")
                        .value("Insufficient funds"));
    }

    private BankAccount createTestAccount() {
        return BankAccount.restore(
                UUID.randomUUID(),
                "Aliahan",
                new BigDecimal("1000.00"),
                AccountCurrency.KZT,
                java.time.Instant.parse("2026-01-01T10:00:00Z")
        );
    }
}