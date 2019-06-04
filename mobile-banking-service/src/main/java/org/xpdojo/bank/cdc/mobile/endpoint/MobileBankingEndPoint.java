package org.xpdojo.bank.cdc.mobile.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.xpdojo.bank.cdc.mobile.domain.Account;

import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class MobileBankingEndPoint {

    @Autowired
    private RestTemplate restTemplate;
    private static final String ACCOUNT_SERVICE = "account-service";
    private static final Logger LOG = LoggerFactory.getLogger(MobileBankingEndPoint.class);

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

}
