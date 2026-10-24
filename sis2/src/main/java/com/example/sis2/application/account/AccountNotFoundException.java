package com.example.sis2.application.account;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(UUID id) {
        super("Bank account not found: " + id);
    }
}