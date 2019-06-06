package org.xpdojo.bank.cdc.account.domain;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

public class AccountConstruction {

    private static final String json1 = "{\"transactions\":[{\"amount\":{\"value\":100.0},\"direction\":\"CREDIT\",\"date\":\"2019-06-06T08:20:43.799395\"},{\"amount\":{\"value\":1000.0},\"direction\":\"DEBIT\",\"date\":\"2019-06-06T08:20:43.803085\"}],\"accountNumber\":30001234,\"description\":\"My private account for dangerous gambling\",\"overdraftFacility\":{\"value\":5000.0}}";
    private static final String json = "\"accountNumber\":30001234,\"description\":\"My private account for dangerous gambling\",\"overdraftFacility\":{\"value\":5000.0}}";
    
    public void buildsAccountsFromJson() throws IOException {
        Jackson2ObjectMapperBuilder.json().build().readValue(json, Account.class);
    }
}
