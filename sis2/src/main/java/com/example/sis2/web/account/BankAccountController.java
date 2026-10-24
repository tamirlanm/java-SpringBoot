package com.example.sis2.web.account;

import com.example.sis2.application.account.BankAccountService;
import com.example.sis2.domain.account.BankAccount;
import com.example.sis2.mapper.account.BankAccountDtoMapper;
import com.example.sis2.web.account.dto.AccountResponse;
import com.example.sis2.web.account.dto.CreateAccountRequest;
import com.example.sis2.web.account.dto.MoneyOperationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService service;
    private final BankAccountDtoMapper mapper;

    public BankAccountController(
            BankAccountService service,
            BankAccountDtoMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(
            @Valid @RequestBody CreateAccountRequest request
    ) {
        BankAccount account = mapper.toDomain(request);

        return mapper.toResponse(service.createAccount(account));
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable UUID id) {
        return mapper.toResponse(service.getAccount(id));
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return service.getAllAccounts()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(
            @PathVariable UUID id,
            @Valid @RequestBody MoneyOperationRequest request
    ) {
        return mapper.toResponse(
                service.deposit(id, request.amount())
        );
    }

    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(
            @PathVariable UUID id,
            @Valid @RequestBody MoneyOperationRequest request
    ) {
        return mapper.toResponse(
                service.withdraw(id, request.amount())
        );
    }
}