package org.xpdojo.bank.cdc.mobile.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class TransferResponseMapperTest {

    private static final String JSON = "{\"response\":\"A message\"}";
    private static final TransferResponse RESPONSE = new TransferResponse("A message");
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJson() throws IOException {
        TransferResponse readRequest = mapper.readValue(JSON, TransferResponse.class);
        assertThat(readRequest).isEqualTo(RESPONSE);
    }

    @Test
    void canWriteToJson() throws JsonProcessingException, JSONException {
        String writtenJson = mapper.writeValueAsString(RESPONSE);
        assertEquals(writtenJson, JSON, JSONCompareMode.STRICT);
    }
}
