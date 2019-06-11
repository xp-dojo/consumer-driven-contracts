package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

class WithdrawalRequestMapperTest {

    private static final String JSON = "{\"accountNumber\":30001234,\"amount\":{\"value\":100.0},\"direction\":\"DEBIT\", \"description\":\"withdrew some money\"}";
    private static final WithdrawalRequest REQUEST = new WithdrawalRequest(30001234L, new Amount(100.0D), "withdrew some money");
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJson() throws IOException {
        WithdrawalRequest readRequest = mapper.readValue(JSON, WithdrawalRequest.class);
        assertThat(readRequest).isEqualTo(REQUEST);
    }

    @Test
    void canWriteToJson() throws JsonProcessingException, JSONException {
        String writtenJson = mapper.writeValueAsString(REQUEST);
        assertEquals(writtenJson, JSON, JSONCompareMode.STRICT);
    }

}