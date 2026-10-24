package com.example.sis2.infrastructure.persistence.account;

import com.example.sis2.application.account.port.BankAccountRepository;
import com.example.sis2.domain.account.BankAccount;
import com.example.sis2.mapper.account.BankAccountMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BankAccountJpaRepositoryAdapter
        implements BankAccountRepository {

    private final BankAccountJpaRepository jpaRepository;
    private final BankAccountMapper mapper;

    public BankAccountJpaRepositoryAdapter(
            BankAccountJpaRepository jpaRepository,
            BankAccountMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public BankAccount save(BankAccount account) {
        BankAccountEntity entity = mapper.toEntity(account);

        BankAccountEntity savedEntity = jpaRepository.save(entity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<BankAccount> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<BankAccount> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}