package org.xpdojo.bank.cdc.mobile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private final Long accountNumber;
    private final String description;
    private final Double overdraftFacility;
    private final Double balance;

    public Account(@JsonProperty("accountNumber") final Long accountNumber,
                   @JsonProperty("description") final String description,
                   @JsonProperty("overdraftFacility") final Money overdraftFacility,
                   @JsonProperty("balance") final Money balance) {
        this.accountNumber = accountNumber;
        this.description = description;
        this.overdraftFacility = overdraftFacility.getValue();
        this.balance = balance.getValue();
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public Double getOverdraftFacility() {
        return overdraftFacility;
    }

    public Double getBalance() {
        return balance;
    }

    public String getAccountDisplay() {
        return accountNumber + " - " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
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
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", description='" + description + '\'' +
                ", overdraftFacility=" + overdraftFacility +
                ", balance=" + balance +
                '}';
    }
}
