package org.xpdojo.bank.cdc.account.endpoint;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Get all accounts",
            notes = "gets all accounts in the repository.  If we had the notion of a customer we would limit to all accounts for a customer.",
            response = Account.class,
            responseContainer = "Collection")
    @GetMapping("/accounts")
    public Collection<Account> getAccounts() {
        return repository.readAccounts();
    }

    @ApiOperation(value = "Create an account",
            notes = "create an account and respond back with that account",
            response = Account.class)
    @PostMapping("/accounts")
    public Account newAccount() {
        return repository.create();
    }

    @ApiOperation(value = "Retrieve a single account",
            notes = "retrieves a single account by its ID",
            response = Account.class)
    @GetMapping("accounts/{accountId}")
    public Account getAccount(@PathVariable Long accountId) {
        return repository.getById(accountId);
    }

    @ApiOperation(value = "Retrieve transactions for an account",
            notes = "retrieves all transactions for a given account",
            response = Transaction.class,
            responseContainer = "Collection")
    @GetMapping("accounts/{accountId}/transactions")
    public Collection<Transaction> getTransactions(@PathVariable Long accountId) {
        return repository.getById(accountId).getTransactions();
    }

    @ApiOperation(value = "Create transaction on an account",
            notes = "Creates a single transcation on an account",
            response = TransactionResponse.class)
    @PostMapping(value = "accounts/{accountId}/transactions")
    public TransactionResponse addTransaction(@PathVariable Long accountId, @RequestBody Transaction transaction) {
        Account account = repository.getById(accountId);
        account.applyTransaction(transaction);
        repository.update(account);
        return new TransactionResponse(accountId, account.balance(), "Thank you for using Dojo Bank");
    }

    @ApiOperation(value = "Retrieves the balance of an account",
            notes = "retrieves the balance of an account from the repository without the transactions",
            response = BalanceResponse.class)
    @GetMapping("accounts/{accountId}/balance")
    public BalanceResponse getBalance(@PathVariable Long accountId) {
        Account account = repository.getById(accountId);
        if (account == null) {
            throw new IllegalStateException("No account exists with an account number of " + accountId);
        }
        return new BalanceResponse(account.getAccountNumber(), account.getAccountDescription(), account.getOverdraftFacility(), account.balance());
    }

}
