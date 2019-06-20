package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class WithdrawalRequest {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final Long accountNumber;
    private final Amount amount;
    private final String direction = "DEBIT";
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateTime;

    public WithdrawalRequest(@JsonProperty("accountNumber") final Long accountNumber,
                             @JsonProperty("amount") final Amount amount,
                             @JsonProperty("dateTime") final LocalDateTime dateTime) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public Amount getAmount() {
        return amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getDirection() {
        return direction;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawalRequest that = (WithdrawalRequest) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(direction, that.direction) &&
                Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amount, direction, dateTime);
    }

    @Override
    public String toString() {
        return "WithdrawalRequest{" +
                "accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", direction='" + direction + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
