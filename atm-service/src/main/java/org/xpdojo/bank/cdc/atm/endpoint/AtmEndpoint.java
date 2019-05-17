package org.xpdojo.bank.cdc.atm.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.xpdojo.bank.cdc.atm.domain.AccountData;
import org.xpdojo.bank.cdc.atm.domain.WithdrawalRequest;
import org.xpdojo.bank.cdc.atm.domain.WithdrawalResponse;

@Controller
public class AtmEndpoint {

    @Autowired
    private RestTemplate restTemplate;
    private static final String ACCOUNT_SERVICE = "account-service";
    private static final Logger LOG = LoggerFactory.getLogger(AtmEndpoint.class);

    // login with an account
    // get account data
    // withdraw cash

    @GetMapping(value = "/atm/accounts/{accountNumber}")
    public String getBalanceOfAccount(@PathVariable Long accountNumber, Model model) {
        model.addAttribute("data", getAccountDataFor(accountNumber));
        model.addAttribute("accountNumber", accountNumber);
        return "accountSummaryView";
    }

    @GetMapping(value ="/atm/accounts/{accountNumber}/withdraw")
    public String getWithdraw(@ModelAttribute WithdrawalRequest withdrawalRequest, @PathVariable Long accountNumber, Model model){
        model.addAttribute("accountNumber", accountNumber);
        model.addAttribute("data", getAccountDataFor(accountNumber));
        return "accountWithdrawalView";
    }

    @PostMapping(value ="/atm/accounts/{accountNumber}/withdraw")
    public String postWithdraw(@ModelAttribute WithdrawalRequest withdrawalRequest, @PathVariable Long accountNumber, Model model){
        model.addAttribute("accountNumber", accountNumber);
        model.addAttribute("response", withDrawFromAccounts(withdrawalRequest));
        return "withdrawalResponse";
    }

    private WithdrawalResponse withDrawFromAccounts(WithdrawalRequest withdrawalRequest) {
        return null;
    }

    private AccountData getAccountDataFor(Long accountNumber) {
        return restTemplate.getForObject(buildBalanceUrl(accountNumber), AccountData.class);
    }

    private String buildBalanceUrl(Long accountNumber) {
        return "http://" + ACCOUNT_SERVICE + "/accounts/" + accountNumber + "/balance";
    }

    private String buildWithdrawalUrl(Long accountNumber) {
        return "http://" + ACCOUNT_SERVICE + "/accounts/" + accountNumber + "/transactions";
    }
}
