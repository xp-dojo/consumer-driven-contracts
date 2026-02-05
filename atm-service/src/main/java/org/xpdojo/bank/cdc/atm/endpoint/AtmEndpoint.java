package org.xpdojo.bank.cdc.atm.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.xpdojo.bank.cdc.atm.domain.AccountData;
import org.xpdojo.bank.cdc.atm.domain.Amount;
import org.xpdojo.bank.cdc.atm.domain.WithdrawalRequest;
import org.xpdojo.bank.cdc.atm.domain.WithdrawalResponse;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class AtmEndpoint {

    @Autowired
    private RestTemplate restTemplate;
    private static final String ACCOUNT_SERVICE = "account-service";

    @GetMapping(value = "/atm/accounts/{accountNumber}")
    public String getBalanceOfAccount(@PathVariable Long accountNumber, Model model) {
        model.addAttribute("data", getAccountDataFor(accountNumber));
        model.addAttribute("accountNumber", accountNumber);
        return "accountSummaryView";
    }

    @GetMapping(value = "/atm/accounts/{accountNumber}/withdraw")
    public String getWithdraw(@PathVariable Long accountNumber, Model model) {
        model.addAttribute("withdrawalRequest", new WithdrawalRequest(accountNumber, new Amount(0D), LocalDateTime.now()));
        model.addAttribute("data", getAccountDataFor(accountNumber));
        return "accountWithdrawalView";
    }

    @PostMapping(value = "/atm/accounts/{accountNumber}/withdraw")
    public String postWithdraw(@ModelAttribute WithdrawalRequest withdrawalRequest, @PathVariable Long accountNumber, Model model) {
        withdrawalRequest.setDateTime(LocalDateTime.now());
        model.addAttribute("response", withdrawFromAccounts(accountNumber, withdrawalRequest));
        return "withdrawalResponse";
    }

    private WithdrawalResponse withdrawFromAccounts(Long accountNumber, WithdrawalRequest withdrawalRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<WithdrawalRequest> entity = new HttpEntity<>(withdrawalRequest, headers);
        return restTemplate.postForObject(buildWithdrawalUrl(accountNumber), entity, WithdrawalResponse.class);
    }

    private String buildWithdrawalUrl(Long accountNumber) {
        return "http://" + ACCOUNT_SERVICE + "/accounts/" + accountNumber + "/transactions";
    }

    private AccountData getAccountDataFor(Long accountNumber) {
        return restTemplate.getForObject(buildBalanceUrl(accountNumber), AccountData.class);
    }

    private String buildBalanceUrl(Long accountNumber) {
        return "http://" + ACCOUNT_SERVICE + "/accounts/" + accountNumber + "/balance";
    }
}
