package org.xpdojo.bank.cdc.account.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.xpdojo.bank.cdc.account.domain.Account.anAccountWith;
import static org.xpdojo.bank.cdc.account.domain.Account.anEmptyAccount;
import static org.xpdojo.bank.cdc.account.domain.AccountBalanceComparator.ofBalances;
import static org.xpdojo.bank.cdc.account.domain.Money.anAmountOf;

public class WithAnAccountWeCan {

    private static final Long ACCOUNT_NUMBER = 2468l;
    private static final Long ANOTHER_ACCOUNT_NUMBER = 13579l;

    @Mock
    StatementWriter statementWriter;

    @BeforeEach
    public void setUpMocks() {
        initMocks(this);
    }

    @Test
    public void compareTwoEmptyAccounts(){
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anEmptyAccount(ANOTHER_ACCOUNT_NUMBER));
    }

    @Test
    public void compareTwoAccountsHaveTheSameBalance() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(10.0d));
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(10.0d)));
    }

    @Test
    public void depositAnAmountToIncreaseTheBalance() {
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        account.deposit(anAmountOf(10.0d));
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(10.0d)));
    }

    @Test
    public void withdrawAnAmountToDecreaseTheBalance() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        account.withdraw(anAmountOf(10.0d));
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(10.0d)));
    }

    @Test
    public void throwsExceptionIfYouTryToWithdrawMoreThanTheBalanceIfNoOverDraft() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        assertThrows(IllegalStateException.class, () -> account.withdraw(anAmountOf(30.0d)));
    }

    @Test
    public void withdrawAnAmountMoreThanBalanceIfWithinOverdraft(){
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(10.0d));
        account.setOverdraftFacility(anAmountOf(100.0d));
        account.withdraw(anAmountOf(60.0d));
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(-50.0d)));
    }

    @Test
    public void throwsExceptionIfYouTryToWithdrawMoreThanYourOverdraft(){
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        account.setOverdraftFacility(anAmountOf(100.0d));
        assertThrows(IllegalStateException.class, () -> account.withdraw(anAmountOf(130.0d)));
    }

    @Test
    public void transferMoneyFromOneAccountToAnother() {
        Account destinationAccount = anEmptyAccount(ACCOUNT_NUMBER);
        Account sourceAccount = anAccountWith(ACCOUNT_NUMBER, anAmountOf(50.0d));

        sourceAccount.transferTo(destinationAccount, anAmountOf(20.0d));

        assertThat(sourceAccount).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(30.0d)));
        assertThat(destinationAccount).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(20.0d)));
    }

    @Test
    public void throwsExceptionIfYouTryToTransferMoreThantheBalance() {
        Account sourceAccount = anAccountWith(ACCOUNT_NUMBER, anAmountOf(20.0d));
        assertThrows(IllegalStateException.class, () -> sourceAccount.transferTo(anEmptyAccount(ANOTHER_ACCOUNT_NUMBER), anAmountOf(30.0d)));
    }

    @Test
    public void hasTheRightBalanceAfterANumberOfTransactions() {
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        account.deposit(anAmountOf(10.0d));
        account.deposit(anAmountOf(80.0d));
        account.deposit(anAmountOf(5.0d));
        account.withdraw(anAmountOf(15.0d));
        account.withdraw(anAmountOf(10.0d));
        assertThat(account).usingComparator(ofBalances()).isEqualTo(anAccountWith(ANOTHER_ACCOUNT_NUMBER, anAmountOf(70.0d)));
    }

    @Test
    public void printOutAnAccountBalance() {
        Account account = anAccountWith(ACCOUNT_NUMBER, anAmountOf(30.0d));
        account.printBalanceStatementWith(statementWriter);
        verify(statementWriter).printBalanceOf(any(Money.class));
    }

    @Test
    public void printOutAFullStatement() {
        Account account = anEmptyAccount(ACCOUNT_NUMBER);
        account.printFullStatementWith(statementWriter);
        verify(statementWriter).printFullStatementWith(anyList());
    }

}
