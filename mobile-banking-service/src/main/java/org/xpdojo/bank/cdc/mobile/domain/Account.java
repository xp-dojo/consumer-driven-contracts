package org.xpdojo.bank.cdc.mobile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private final Long accountNumber;
    private final Double overdraftFacility;
    private final Double balance;

    public Account(@JsonProperty("accountNumber") final Long accountNumber,
                   @JsonProperty("overdraftFacility") final Money overdraftFacility,
                   @JsonProperty("balance") final Money balance) {
        this.accountNumber = accountNumber;
        this.overdraftFacility = overdraftFacility.getValue();
        this.balance = balance.getValue();
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Double getOverdraftFacility() {
        return overdraftFacility;
    }

    public Double getBalance() {
        return balance;
    }

    public String getAccountDisplay() {
        return accountNumber + " - ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(overdraftFacility, that.overdraftFacility) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, overdraftFacility, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", overdraftFacility=" + overdraftFacility +
                ", balance=" + balance +
                '}';
    }
}
