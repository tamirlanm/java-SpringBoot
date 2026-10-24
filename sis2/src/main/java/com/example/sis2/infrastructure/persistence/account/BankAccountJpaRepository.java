package com.example.sis2.infrastructure.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountJpaRepository
        extends JpaRepository<BankAccountEntity, UUID> {
}