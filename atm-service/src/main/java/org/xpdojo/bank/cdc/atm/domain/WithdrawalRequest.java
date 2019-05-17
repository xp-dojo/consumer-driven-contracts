package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class WithdrawalRequest {

    private Long accountNumber;
    private Double amount;
    private final String direction = "DEBIT";
    private final String description = "Withdrawal from ATM on " + LocalDateTime.now();

    public WithdrawalRequest() {
    }

    public WithdrawalRequest(final Long accountNumber, final Double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "WithdrawalRequest{" +
                "amount=" + amount +
                ", direction='" + direction + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
