package org.xpdojo.bank.cdc.account.domain;

import java.util.List;

public interface StatementWriter {

    void printBalanceOf(Money balance);

    void printFullStatementWith(List<Transaction> transactions);


}
