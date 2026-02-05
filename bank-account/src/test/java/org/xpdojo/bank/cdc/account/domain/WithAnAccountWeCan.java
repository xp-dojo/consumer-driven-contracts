package org.xpdojo.bank.cdc.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.xpdojo.bank.cdc.account.domain.Account.anAccountWith;
import static org.xpdojo.bank.cdc.account.domain.Account.anEmptyAccount;
import static org.xpdojo.bank.cdc.account.domain.AccountBalanceComparator.ofBalances;
import static org.xpdojo.bank.cdc.account.domain.Money.anAmountOf;

class WithAnAccountWeCan {

    private static final Long ACCOUNT_NUMBER = 2468L;
    private static final Long ANOTHER_ACCOUNT_NUMBER = 13579L;

    @Mock
    StatementWriter statementWriter;

    @BeforeEach
    void setUpMocks() {
        openMocks(this);
    }

    @Test
    void compareTwoEmptyAccounts() {
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anEmptyAccount(ANOTHER_ACCOUNT_NUMBER));
    }

    @Test
    void compareTwoAccountsHaveTheSameBalance() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(10.0d));
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(10.0d)));
    }

    @Test
    void depositAnAmountToIncreaseTheBalance() {
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        account.deposit(anAmountOf(10.0d), LocalDateTime.now());
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(10.0d)));
    }

    @Test
    void withdrawAnAmountToDecreaseTheBalance() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        account.withdraw(anAmountOf(10.0d), LocalDateTime.now());
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(10.0d)));
    }

    @Test
    void throwsExceptionIfYouTryToWithdrawMoreThanTheBalanceIfNoOverDraft() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        assertThrows(IllegalStateException.class, () -> account.withdraw(anAmountOf(30.0d), LocalDateTime.now()));
    }

    @Test
    void withdrawAnAmountMoreThanBalanceIfWithinOverdraft() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(10.0d));
        account.setOverdraftFacility(anAmountOf(100.0d));
        account.withdraw(anAmountOf(60.0d), LocalDateTime.now());
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(-50.0d)));
    }

    @Test
    void throwsExceptionIfYouTryToWithdrawMoreThanYourOverdraft() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        account.setOverdraftFacility(anAmountOf(100.0d));
        assertThrows(IllegalStateException.class, () -> account.withdraw(anAmountOf(130.0d), LocalDateTime.now()));
    }

    @Test
    void transferMoneyFromOneAccountToAnother() {
        Account destinationAccount = anEmptyAccount(ACCOUNT_NUMBER);
        Account sourceAccount = anAccountWith(ACCOUNT_NUMBER, anAmountOf(50.0d));

        sourceAccount.transferTo(destinationAccount, anAmountOf(20.0d), LocalDateTime.now());

        assertThat(sourceAccount).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(30.0d)));
        assertThat(destinationAccount).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(20.0d)));
    }

    @Test
    void throwsExceptionIfYouTryToTransferMoreThantheBalance() {
        Account sourceAccount = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        assertThrows(IllegalStateException.class, () -> sourceAccount.transferTo(anEmptyAccount(ANOTHER_ACCOUNT_NUMBER), anAmountOf(30.0d), LocalDateTime.now()));
    }

    @Test
    void hasTheRightBalanceAfterANumberOfTransactions() {
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        account.deposit(anAmountOf(10.0d), LocalDateTime.now());
        account.deposit(anAmountOf(80.0d), LocalDateTime.now());
        account.deposit(anAmountOf(5.0d), LocalDateTime.now());
        account.withdraw(anAmountOf(15.0d), LocalDateTime.now());
        account.withdraw(anAmountOf(10.0d), LocalDateTime.now());
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(70.0d)));
    }

    @Test
    void printOutAnAccountBalance() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(30.0d));
        account.printBalanceStatementWith(statementWriter);
        verify(statementWriter).printBalanceOf(any(Money.class));
    }

    @Test
    void printOutAFullStatement() {
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        account.printFullStatementWith(statementWriter);
        verify(statementWriter).printFullStatementWith(anyList());
    }

}
