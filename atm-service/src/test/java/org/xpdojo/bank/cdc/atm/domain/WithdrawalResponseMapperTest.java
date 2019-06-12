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

class WithdrawalResponseMapperTest {

    private static final String JSON = "{\"accountNumber\":30001234,\"response\":\"A message\",\"balance\":{\"value\":-910.0}}";
    private static final WithdrawalResponse RESPONSE = new WithdrawalResponse(30001234L, "A message", new Amount(-910.0D));
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreateFromJson() throws IOException {
        WithdrawalResponse readResponse = mapper.readValue(JSON, WithdrawalResponse.class);
        assertThat(readResponse).isEqualTo(RESPONSE);
    }

    @Test
    void canWriteToJson() throws JsonProcessingException, JSONException {
        String writtenJson = mapper.writeValueAsString(RESPONSE);
        assertEquals(JSON, writtenJson, JSONCompareMode.STRICT);
    }


}