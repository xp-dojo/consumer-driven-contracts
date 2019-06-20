package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class WithdrawalResponse {

    private final Long accountNumber;
    private final String response;
    private final Amount balance;

    public WithdrawalResponse(@JsonProperty("accountNumber") final Long accountNumber,
                              @JsonProperty("response") final String response,
                              @JsonProperty("balance") final Amount balance) {
        this.accountNumber = accountNumber;
        this.response = response;
        this.balance = balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getResponse() {
        return response;
    }

    public Amount getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawalResponse that = (WithdrawalResponse) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(response, that.response) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, response, balance);
    }

    @Override
    public String toString() {
        return "WithdrawalResponse{" +
                "accountNumber=" + accountNumber +
                ", response='" + response + '\'' +
                ", balance=" + balance +
                '}';
    }
}
