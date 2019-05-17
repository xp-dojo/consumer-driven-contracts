package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TransactionResponse {
    private final Long accountNumber;
    private final Money resultingBalance;
    private final String response;

    public TransactionResponse(@JsonProperty("accountNumber") final Long accountNumber,
                               @JsonProperty("balance") final Money resultingBalance,
                               @JsonProperty("response") final String response) {
        this.accountNumber = accountNumber;
        this.resultingBalance = resultingBalance;
        this.response = response;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Money getResultingBalance() {
        return resultingBalance;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponse that = (TransactionResponse) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(resultingBalance, that.resultingBalance) &&
                Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, resultingBalance, response);
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "accountNumber=" + accountNumber +
                ", resultingBalance=" + resultingBalance +
                ", response='" + response + '\'' +
                '}';
    }
}
