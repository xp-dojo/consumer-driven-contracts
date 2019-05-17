package org.xpdojo.bank.cdc.account.endpoint;

import org.springframework.web.bind.annotation.*;
import org.xpdojo.bank.cdc.account.domain.Account;
import org.xpdojo.bank.cdc.account.domain.BalanceResponse;
import org.xpdojo.bank.cdc.account.domain.Transaction;
import org.xpdojo.bank.cdc.account.domain.TransactionResponse;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class BankAccountEndpoint {

    private final AccountRepository repository = new AccountRepository();

    @GetMapping("/accounts")
    public Collection<Account> getAccounts() {
        return repository.readAccounts();
    }

    @PostMapping("/accounts")
    public Account newAccount() {
        return repository.create();
    }

    @GetMapping("accounts/{accountId}")
    public Account getAccount(@PathVariable Long accountId) {
        return repository.getById(accountId);
    }

    @GetMapping("accounts/{accountId}/transactions")
    public Collection<Transaction> getTransactions(@PathVariable Long accountId) {
        return repository.getById(accountId).getTransactions();
    }

    @PostMapping("accounts/{accountId}/transactions")
    public TransactionResponse addTransaction(@PathVariable Long accountId, @RequestBody Transaction transaction) {
        Account account = repository.getById(accountId);
        account.applyTransaction(transaction);
        repository.update(account);
        return new TransactionResponse(accountId, account.balance(), "Thank you for using Dojo Bank");
    }

    @GetMapping("accounts/{accountId}/balance")
    public BalanceResponse getBalance(@PathVariable Long accountId){
        Account account = repository.getById(accountId);
        if(account == null){
            throw new IllegalStateException("No account exists with an account number of " + accountId);
        }
        return new BalanceResponse(account.getAccountNumber(), account.getAccountDescription(), account.getOverdraftFacility(), account.balance());
    }

}
