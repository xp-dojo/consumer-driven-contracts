package org.xpdojo.bank.cdc.atm.domain;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

class WithdrawalRequestTest {


    @Test
    void createdFromXml() throws IOException {
        Jackson2ObjectMapperBuilder.json().build().readValue(createJsonString(), WithdrawalRequest.class);
    }

    private String createJsonString() {
        return "{\"accountNumber\":30001234,\"amount\":{\"value\":10.0},\"direction\":\"DEBIT\",\"description\":\"Withdrawal from ATM on 2019-06-03T07:08:47.012902\"}";
    }

}