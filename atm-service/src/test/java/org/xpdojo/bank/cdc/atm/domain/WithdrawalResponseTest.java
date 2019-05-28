package org.xpdojo.bank.cdc.atm.domain;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class WithdrawalResponseTest {

    @Test
    void createFromJson() throws IOException {
        WithdrawalResponse response = Jackson2ObjectMapperBuilder.json().build().readValue(createJsonString(), WithdrawalResponse.class);
        assertThat(response.getAccountNumber()).isEqualTo(30001234L);
        assertThat(response.getResponse()).isNotEmpty();
        assertThat(response.getBalance()).isEqualTo(new Amount(-910.0D));
    }

    private String createJsonString() {
        return "{\"accountNumber\":30001234,\"response\":\"Thank you for using Dojo Bank\",\"resultingBalance\":{\"value\":-910.0}}";
    }

}