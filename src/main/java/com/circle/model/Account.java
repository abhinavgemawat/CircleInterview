package com.circle.model;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account{
    @JsonProperty
    private long accountNumber;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private BigDecimal balance;

    public Account() {
        super();
    }
    public long getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public Account(long number, String name, BigDecimal balance) {
        this.accountNumber = number;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return String.format("Account (accNumber=%d, name=%s, balance=%f)", this.accountNumber, this.name, this.balance);
    }


    // private long createAccountId() {
    // }
    
}
