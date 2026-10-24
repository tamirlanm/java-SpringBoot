package com.example.sis2.mapper.account;

import com.example.sis2.domain.account.BankAccount;
import com.example.sis2.infrastructure.persistence.account.BankAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccountEntity toEntity(BankAccount account) {
        return new BankAccountEntity(
                account.getId(),
                account.getAccountHolder(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreatedAt()
        );
    }

    public BankAccount toDomain(BankAccountEntity entity) {
        return BankAccount.restore(
                entity.getId(),
                entity.getAccountHolder(),
                entity.getBalance(),
                entity.getCurrency(),
                entity.getCreatedAt()
        );
    }
}