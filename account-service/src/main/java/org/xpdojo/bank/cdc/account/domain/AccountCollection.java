package org.xpdojo.bank.cdc.account.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountCollection {

    private final List<AccountSummary> accounts = new ArrayList<>();

    public AccountCollection(List<AccountSummary> accounts){
        this.accounts.addAll(accounts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCollection that = (AccountCollection) o;
        return Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts);
    }

    @Override
    public String toString() {
        return "AccountCollection{" +
                "accounts=" + accounts +
                '}';
    }
}
