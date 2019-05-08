package org.xpdojo.bank.cdc.account.endpoint;

import org.springframework.web.bind.annotation.*;
import org.xpdojo.bank.cdc.account.domain.Account;
import org.xpdojo.bank.cdc.account.domain.Transaction;

import java.util.Collection;

@RestController
public class BankAccountEndpoint {

    private final AccountRepository repository = new AccountRepository();

    @GetMapping("/accounts")
    public Collection<Account> getAccounts(){
        return repository.readAccounts();
    }

    @PostMapping("/accounts")
    public Account newAccount(@RequestBody AccountData accountData) {
        return repository.insert(accountData);
    }

    @GetMapping("accounts/{accountId}")
    public Account getAccount(@PathVariable Long accountId){
        return repository.getById(accountId);
    }

    @GetMapping("accounts/{accountId}/transactions")
    public Collection<Transaction> getTransactions(@PathVariable Long accountId){
        return repository.getById(accountId).getTransactions();
    }

    @PostMapping("accounts/{accountId}/transactions")
    public Account addTransaction(@PathVariable Long accountId, @RequestBody Transaction transaction){
        Account account = repository.getById(accountId);
        account.addTransaction(transaction);
        repository.update(account);
        return account;
    }

}
