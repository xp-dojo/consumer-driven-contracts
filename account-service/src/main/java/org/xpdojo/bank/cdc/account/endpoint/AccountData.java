package org.xpdojo.bank.cdc.account.endpoint;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * This class only exists so we can enforce the principle that every account number have an account number. This is
 * only used to request a new account with an opening balance.
 */
@JsonAutoDetect(fieldVisibility = ANY)
class AccountData {

    private final double openingBalance;

    public AccountData(@JsonProperty("openingBalance") double anOpeningBalance) {
        openingBalance = anOpeningBalance;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountData that = (AccountData) o;
        return Double.compare(that.openingBalance, openingBalance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(openingBalance);
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "openingBalance=" + openingBalance +
                '}';
    }
}
