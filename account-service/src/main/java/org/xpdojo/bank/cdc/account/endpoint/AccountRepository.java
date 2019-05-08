package org.xpdojo.bank.cdc.account.endpoint;

import org.xpdojo.bank.cdc.account.domain.Account;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.xpdojo.bank.cdc.account.domain.Account.anAccountWith;
import static org.xpdojo.bank.cdc.account.domain.Account.anEmptyAccount;
import static org.xpdojo.bank.cdc.account.domain.Money.anAmountOf;

public class AccountRepository {

    private static final Long MIN = 12340000l;
    private static final Long MAX = 12349999l;
    private final Map<Long, Account> accounts = new HashMap<>();

    {
        Account account = anAccountWith(1234l, anAmountOf(100.0d));
        account.deposit(anAmountOf(10.0d));
        accounts.put(account.getAccountNumber(), account);

        Account otherAccount = anEmptyAccount(5678l);
        otherAccount.deposit(anAmountOf(120.0d));
        accounts.put(otherAccount.getAccountNumber(), otherAccount);
    }

    public Account create() {
        Account account = anEmptyAccount(createRandomBankAccountNumber());
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public Collection<Account> readAccounts() {
        return accounts.values();
    }

    public Account getById(Long accountId) {
        return accounts.get(accountId);
    }

    private final Long createRandomBankAccountNumber() {
        return MIN + (long) (Math.random() * (MAX - MIN));
    }

    public void update(Account account) {
        accounts.replace(account.getAccountNumber(), account);
    }
}
