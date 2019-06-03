package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.lang.Double.valueOf;

public class Amount {
    private final double value;

    public Amount(@JsonProperty("value") final Double value) {
        this.value = value;
    }

    public Amount(final String value) {
        this.value = valueOf(value);
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return Objects.equals(value, amount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                '}';
    }
}
