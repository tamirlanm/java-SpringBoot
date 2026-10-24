package com.example.sis2.mapper.account;

import com.example.sis2.domain.account.BankAccount;
import com.example.sis2.web.account.dto.AccountResponse;
import com.example.sis2.web.account.dto.CreateAccountRequest;
import org.springframework.stereotype.Component;

@Component
public class BankAccountDtoMapper {

    public BankAccount toDomain(CreateAccountRequest request) {
        return BankAccount.open(
                request.accountHolder(),
                request.initialBalance(),
                request.currency()
        );
    }

    public AccountResponse toResponse(BankAccount account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountHolder(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreatedAt()
        );
    }
}