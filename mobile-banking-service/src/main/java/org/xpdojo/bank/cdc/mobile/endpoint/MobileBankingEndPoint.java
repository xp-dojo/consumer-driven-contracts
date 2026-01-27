package org.xpdojo.bank.cdc.mobile.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.xpdojo.bank.cdc.mobile.domain.Account;
import org.xpdojo.bank.cdc.mobile.domain.TransferRequest;
import org.xpdojo.bank.cdc.mobile.domain.TransferResponse;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class MobileBankingEndPoint {

    @Autowired
    private RestTemplate restTemplate;
    private static final String ACCOUNT_SERVICE = "account-service";

    @GetMapping(value = "/mobile/accounts")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", getAllAccounts());
        return "accountsListView";
    }

    @GetMapping(value = "/mobile/accounts/{accountNumber}")
    public String getBalanceOfAccount(@PathVariable Long accountNumber, Model model) {
        model.addAttribute("data", getAccountDataFor(accountNumber));
        model.addAttribute("accountNumber", accountNumber);
        return "accountSummaryView";
    }

    @GetMapping(value = "/mobile/accounts/transfers")
    public String getTransfer(@ModelAttribute TransferRequest transferRequest, Model model) {
        model.addAttribute("accounts", getAllAccounts());
        return "transferRequestView";
    }

    @PostMapping(value = "/mobile/accounts/transfers")
    public String postWithdraw(@ModelAttribute TransferRequest transferRequest, Model model) {
        transferRequest.setDateTime(LocalDateTime.now());
        model.addAttribute("response", transferAcrossAccounts(transferRequest));
        return "transferResponseView";
    }

    private TransferResponse transferAcrossAccounts(TransferRequest transferRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<TransferRequest> entity = new HttpEntity<>(transferRequest, headers);
        return restTemplate.postForObject(buildTransferUrl(), entity, TransferResponse.class);
    }

    private List<Account> getAllAccounts() {
        return asList(restTemplate.getForObject(buildGetAccountsUrl(), Account[].class));
    }

    private Account getAccountDataFor(Long accountNumber) {
        return restTemplate.getForObject(buildBalanceUrl(accountNumber), Account.class);
    }


    private String buildBalanceUrl(Long accountNumber) {
        return "http://" + ACCOUNT_SERVICE + "/accounts/" + accountNumber + "/balance";
    }

    private String buildGetAccountsUrl() {
        return "http://" + ACCOUNT_SERVICE + "/accounts";
    }

    private String buildTransferUrl() {
        return "http://" + ACCOUNT_SERVICE + "/accounts/transfers";
    }

}
