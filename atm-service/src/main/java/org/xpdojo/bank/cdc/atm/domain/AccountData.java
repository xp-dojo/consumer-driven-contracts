package org.xpdojo.bank.cdc.atm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountData {

    private final Long accountNumber;
    private final String description;
    private final Double overdraftFacility;
    private final Double balance;

    public AccountData(@JsonProperty("accountNumber") final Long accountNumber,
                       @JsonProperty("description") final String description,
                       @JsonProperty("overdraftFacility") final Amount overdraftFacility,
                       @JsonProperty("balance") final Amount balance) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountData that = (AccountData) o;
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
        return "AccountData{" +
                "accountNumber=" + accountNumber +
                ", description='" + description + '\'' +
                ", overdraftFacility=" + overdraftFacility +
                ", balance=" + balance +
                '}';
    }


}

