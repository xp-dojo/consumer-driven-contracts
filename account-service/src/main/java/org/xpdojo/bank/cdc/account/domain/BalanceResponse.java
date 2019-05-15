package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class BalanceResponse {

    private final Long accountNumber;
    private final String description;
    private final Money overdraftFacility;
    private final Money balance;

    public BalanceResponse(@JsonProperty("accountNumber") final Long accountNumber,
                           @JsonProperty("description") final String description,
                           @JsonProperty("overdraftFacility") final Money overdraftFacility,
                           @JsonProperty("balance") final Money balance) {
        this.accountNumber = accountNumber;
        this.description = description;
        this.overdraftFacility = overdraftFacility;
        this.balance = balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public Money getBalance() {
        return balance;
    }

    public Money getOverdraftFacility() {
        return overdraftFacility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceResponse that = (BalanceResponse) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(description, that.description) &&
                Objects.equals(overdraftFacility, that.overdraftFacility) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, description, overdraftFacility, balance);
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "accountNumber=" + accountNumber +
                ", description='" + description + '\'' +
                ", overdraftFacility=" + overdraftFacility +
                ", balance=" + balance +
                '}';
    }
}
