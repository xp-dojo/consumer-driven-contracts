package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static java.util.Objects.hash;
import static org.xpdojo.bank.cdc.account.domain.Money.anAmountOf;
import static org.xpdojo.bank.cdc.account.domain.Transaction.*;

@JsonAutoDetect(fieldVisibility = ANY)
public class Account {

    private final Long accountNumber;
    private final List<Transaction> transactions = new ArrayList<>();

    public static Account anEmptyAccount(final Long accountNumber) {
        return new Account(accountNumber);
    }

    public static Account anAccountWith(final Long accountNumber, final Money amount) {
        return new Account(accountNumber, amount);
    }

    private Account(@JsonProperty("accountNumber") final Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    private Account(final Long accountNumber, final Money anAmount) {
        this(accountNumber);
        transactions.add(anOpeningBalanceOf(anAmount, LocalDateTime.now()));
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Money balance() {
        if (transactions.size() == 0) {
            return anAmountOf(0.0d);
        }
        return transactions.stream().map(transaction -> transaction.balanceImpact()).reduce(Money::add).get();
    }

    public void addTransaction(final Transaction transaction){
        transactions.add(transaction);
    }

    public void deposit(final Money anAmount) {
        transactions.add(aDepositOf(anAmount, LocalDateTime.now()));
    }

    public void withdraw(final Money anAmount) {
        if (balance().isLessThan(anAmount)) {
            throw new IllegalStateException("You cannot withdraw more than the balance");
        }
        transactions.add(aWithDrawlOf(anAmount, LocalDateTime.now()));
    }

    public void transferTo(final Account destinationAccount, final Money money) {
        destinationAccount.deposit(money);
        this.withdraw(money);
    }

    public void printBalanceStatementWith(StatementWriter writer) {
        writer.printBalanceOf(balance());
    }

    public void printFullStatementWith(StatementWriter writer) {
        writer.printFullStatementWith(transactions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return hash(accountNumber, transactions);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", transactions=" + transactions +
                '}';
    }
}
