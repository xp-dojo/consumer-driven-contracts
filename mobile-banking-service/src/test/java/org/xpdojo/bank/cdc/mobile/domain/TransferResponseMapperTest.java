package org.xpdojo.bank.cdc.mobile.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private Gson gson = new GsonBuilder().create();

    @Test
    void canBeCreatedFromJsonUsingJackson() throws IOException {
        TransferResponse readRequest = mapper.readValue(JSON, TransferResponse.class);
        assertThat(readRequest).isEqualTo(RESPONSE);
    }

    @Test
    void canWriteToJsonUsingJackson() throws JsonProcessingException, JSONException {
        String writtenJson = mapper.writeValueAsString(RESPONSE);
        assertEquals(JSON, writtenJson, JSONCompareMode.STRICT);
    }

}