CREATE TABLE bank_accounts (
    id UUID PRIMARY KEY,
    account_holder VARCHAR(120) NOT NULL,
    balance NUMERIC(19, 2) NOT NULL CHECK (balance >= 0),
    currency VARCHAR(3) NOT NULL
    CHECK (currency IN ('KZT', 'USD', 'EUR')),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);