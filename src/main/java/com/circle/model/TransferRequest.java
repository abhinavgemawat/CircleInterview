package com.circle.model;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

    @JsonProperty
    private long fromAccountNumber;

    @JsonProperty
    private long toAccountNumber;

    @JsonProperty
    private BigDecimal amount;

    public TransferRequest() {
        super();
    }

    public long getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TransferRequest request = (TransferRequest) o;
        return (Objects.equals(toAccountNumber, request.toAccountNumber) && Objects.equals(fromAccountNumber, request.fromAccountNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccountNumber);
    }

    @Override
    public String toString() {
        return String.format("TransferRequest (fromAccountNumber=%d, toAccountNumber=%d, amount=%f)", this.fromAccountNumber, this.toAccountNumber, this.amount);
    }
}
