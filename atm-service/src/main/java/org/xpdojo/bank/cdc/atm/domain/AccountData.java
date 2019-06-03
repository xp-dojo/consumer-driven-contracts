package org.xpdojo.bank.cdc.atm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountData {

    private final Long accountNumber;
    private final String accountDescription;
    private final Double overdraftFacility;
    private final Double balance;

    public AccountData(@JsonProperty("accountNumber") final Long accountNumber,
                       @JsonProperty("description") final String accountDescription,
                       @JsonProperty("overdraftFacility") final Amount overdraftFacility,
                       @JsonProperty("balance") final Amount balance) {
        this.accountNumber = accountNumber;
        this.accountDescription = accountDescription;
        this.overdraftFacility = overdraftFacility.getValue();
        this.balance = balance.getValue();
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getAccountDescription() {
        return accountDescription;
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
                Objects.equals(accountDescription, that.accountDescription) &&
                Objects.equals(overdraftFacility, that.overdraftFacility) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountDescription, overdraftFacility, balance);
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "accountNumber=" + accountNumber +
                ", accountDescription='" + accountDescription + '\'' +
                ", overdraftFacility=" + overdraftFacility +
                ", balance=" + balance +
                '}';
    }


}

