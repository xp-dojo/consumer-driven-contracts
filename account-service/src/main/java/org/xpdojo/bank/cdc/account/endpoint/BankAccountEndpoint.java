package org.xpdojo.bank.cdc.account.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.xpdojo.bank.cdc.account.domain.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class BankAccountEndpoint {

    private final AccountRepository repository = new AccountRepository();
    private static final Logger LOG = LoggerFactory.getLogger(BankAccountEndpoint.class);

    @Operation(summary = "Get all accounts", description = "gets all accounts in the repository.  If we had the notion of a customer we would limit to all accounts for a customer.")
    @GetMapping("/accounts")
    public Collection<AccountSummary> getAccounts() {
        return repository
                .readAccounts()
                .stream()
                .map(acc -> new AccountSummary(acc.getAccountNumber(), acc.getDescription(), acc.getOverdraftFacility(), acc.balance()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Retrieve transactions for an account", description = "retrieves all transactions for a given account")
    @GetMapping(value = "/accounts/{accountId}/transactions", produces = APPLICATION_JSON_VALUE)
    public Collection<Transaction> getTransactions(@PathVariable Long accountId) {
        return repository.getById(accountId).getTransactions();
    }

    @Operation(summary = "Create transaction on an account", description = "Creates a single transcation on an account")
    @PostMapping(value = "/accounts/{accountId}/transactions", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse addTransaction(@PathVariable Long accountId, @RequestBody Transaction transaction) {
        Account account = repository.getById(accountId);
        account.applyTransaction(transaction);
        repository.update(account);
        return new TransactionResponse(accountId, account.balance(), "Thank you for using Dojo Bank");
    }

    @Operation(summary = "Retrieves the balance of an account", description = "retrieves the balance of an account from the repository without the transactions")
    @GetMapping(value = "/accounts/{accountId}/balance", produces = APPLICATION_JSON_VALUE)
    public AccountSummary getBalance(@PathVariable Long accountId) {
        Account account = repository.getById(accountId);
        if (account == null) {
            throw new IllegalStateException("No account exists with an account number of " + accountId);
        }
        return new AccountSummary(account.getAccountNumber(), account.getDescription(), account.getOverdraftFacility(), account.balance());
    }

    @Operation(summary = "creates a transfer between two accounts")
    @PostMapping(value = "/accounts/transfers", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse processTransfer(@RequestBody TransferRequest request) {
        Account fromAccount = repository.getById(request.getFromAccount());
        Account toAccount = repository.getById(request.getToAccount());
        fromAccount.transferTo(toAccount, request.getAmount(), request.getDateTime());
        repository.update(fromAccount);
        repository.update(toAccount);
        return new TransferResponse(request.getAmount().value() + " transferred from " + fromAccount.getAccountNumber() + " to " + toAccount.getAccountNumber() + ", thank you for using Dojo Bank");
    }

}
