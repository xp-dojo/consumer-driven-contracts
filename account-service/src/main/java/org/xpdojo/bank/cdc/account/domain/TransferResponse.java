package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TransferResponse {
    private final String response;

    public TransferResponse(@JsonProperty("response") final String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferResponse that = (TransferResponse) o;
        return Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response);
    }

    @Override
    public String toString() {
        return "TransferResponse{" +
                "response='" + response + '\'' +
                '}';
    }
}
