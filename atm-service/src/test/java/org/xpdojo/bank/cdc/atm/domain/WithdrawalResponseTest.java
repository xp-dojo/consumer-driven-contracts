package org.xpdojo.bank.cdc.atm.domain;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

class WithdrawalResponseTest {

    @Test
    void createFromJson() throws IOException {
        Jackson2ObjectMapperBuilder.json().build().readValue(createJsonString(), WithdrawalResponse.class);
    }

    private String createJsonString() {
        return "{\"accountNumber\":30001234,\"response\":\"Thank you for using Dojo Bank\",\"resultingBalance\":{\"value\":-910.0}}";
    }

}