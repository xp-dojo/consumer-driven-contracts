package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

class WithdrawalRequestMapperTest {

    private static final LocalDateTime TEST_TIME = LocalDateTime.of(2019, Month.JUNE, 01, 13, 35, 23);
    private static final String CREATE_JSON = "{\"accountNumber\":30001234,\"amount\":{\"value\":100.0},\"description\":\"ATM withdrawl\",\"direction\":\"DEBIT\", \"dateTime\":\"2019-06-01T13:35:23\"}";
    private static final String READ_JSON = "{\"amount\":{\"value\":100.0},\"description\":\"ATM withdrawl\",\"direction\":\"DEBIT\", \"dateTime\":\"2019-06-01T13:35:23\"}";
    private static final WithdrawalRequest REQUEST = new WithdrawalRequest(30001234L, new Amount(100.0D), "ATM withdrawl", TEST_TIME);
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJson() throws IOException {
        WithdrawalRequest readRequest = mapper.readValue(CREATE_JSON, WithdrawalRequest.class);
        assertThat(readRequest).isEqualTo(REQUEST);
    }

    @Test
    void canWriteToJson() throws JsonProcessingException, JSONException {
        String writtenJson = mapper.writeValueAsString(REQUEST);
        assertEquals(READ_JSON, writtenJson, JSONCompareMode.STRICT);
    }

}