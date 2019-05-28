package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class WithdrawalResponse {

    private final String response;
    private final Amount balance;

    public WithdrawalResponse(@JsonProperty("response") String response,
                              @JsonProperty("resultingBalance") Amount balance) {
        this.response = response;
        this.balance = balance;
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
        return Objects.equals(response, that.response) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, balance);
    }

    @Override
    public String toString() {
        return "WithdrawalResponse{" +
                "response='" + response + '\'' +
                ", balance=" + balance +
                '}';
    }
}
