package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static java.lang.Double.valueOf;
import static java.util.Objects.hash;

@JsonAutoDetect(fieldVisibility = ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Money implements Comparable<Money> {

    private final double value;

    private Money(@JsonProperty("value") final double anAmount) {
        this.value = anAmount;
    }

    public static Money anAmountOf(final double anAmount) {
        return new Money(anAmount);
    }

    public Money add(final Money anAmount) {
        return anAmountOf(this.value + anAmount.value);
    }

    public Money less(final Money anAmount) {
        return anAmountOf(this.value - anAmount.value);
    }

    public boolean isLessThan(Money anAmount) {
        return value < anAmount.value;
    }

    Money negative() {
        return anAmountOf(value * -1);
    }

    public double value(){
        return value;
    }

    @Override
    public int compareTo(Money otherAmount) {
        return valueOf(value).compareTo(valueOf(otherAmount.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return hash(value);
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                '}';
    }
}
