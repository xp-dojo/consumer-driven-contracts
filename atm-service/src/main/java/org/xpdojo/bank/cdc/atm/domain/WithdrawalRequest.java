package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithdrawalRequest {

    private Long accountNumber;
    private Amount amount;
    private final String direction = "DEBIT";

    public WithdrawalRequest(@JsonProperty("accountNumber") final Long accountNumber,
                             @JsonProperty("amount") final Amount amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawalRequest that = (WithdrawalRequest) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amount, direction);
    }

    @Override
    public String toString() {
        return "WithdrawalRequest{" +
                "accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", direction='" + direction + '\'' +
                '}';
    }
}
