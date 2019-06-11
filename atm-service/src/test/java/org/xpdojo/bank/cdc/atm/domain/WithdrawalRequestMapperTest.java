package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class WithdrawalRequestMapperTest {

    private static final String JSON = "{\"accountNumber\":30001234,\"amount\":{\"value\":100.0},\"direction\":\"DEBIT\"}";
    private static final WithdrawalRequest REQUEST = new WithdrawalRequest(30001234L, new Amount(100.0D));
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJson() throws IOException {
        WithdrawalRequest readRequest = mapper.readValue(JSON, WithdrawalRequest.class);
        assertThat(readRequest).isEqualTo(REQUEST);
    }

    @Test
    void canWriteToJson() throws JsonProcessingException {
        String writtenJson = mapper.writeValueAsString(REQUEST);
        assertThat(writtenJson).isEqualTo(JSON);
    }

}