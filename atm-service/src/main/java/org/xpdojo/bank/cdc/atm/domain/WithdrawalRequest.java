package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class WithdrawalRequest {

    private Double amount;
    private final String direction = "DEBIT";
    private final LocalDateTime date = LocalDateTime.now();
    private final String description = "Withdrawl from ATM on " + date;

    public WithdrawalRequest() {
    }

    public WithdrawalRequest(final Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "WithdrawalRequest{" +
                "amount=" + amount +
                ", direction='" + direction + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
