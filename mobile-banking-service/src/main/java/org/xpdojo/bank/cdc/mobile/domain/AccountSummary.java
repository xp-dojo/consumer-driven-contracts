package org.xpdojo.bank.cdc.mobile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountSummary {
    private final Long accountNumber;
    private final String accountDescription;
    private final Double overdraftFacility;
    private final Double balance;

    public AccountSummary(@JsonProperty("accountNumber") final Long accountNumber,
                          @JsonProperty("description") final String accountDescription,
                          @JsonProperty("overdraftFacility") final Money overdraftFacility,
                          @JsonProperty("balance") final Money balance) {
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
        AccountSummary that = (AccountSummary) o;
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
        return "AccountSummary{" +
                "accountNumber=" + accountNumber +
                ", accountDescription='" + accountDescription + '\'' +
                ", overdraftFacility=" + overdraftFacility +
                ", balance=" + balance +
                '}';
    }
}
