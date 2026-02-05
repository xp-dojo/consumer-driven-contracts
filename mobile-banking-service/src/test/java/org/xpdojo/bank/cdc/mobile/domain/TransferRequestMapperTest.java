package org.xpdojo.bank.cdc.mobile.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class TransferRequestMapperTest {

    private final LocalDateTime TEST_TIME = LocalDateTime.of(2019, Month.JUNE, 01, 13, 35, 23);
    private final String JSON = "{\"fromAccount\":30005432,\"toAccount\":30005678,\"amount\":{\"value\":10},\"description\":\"description\",\"dateTime\":\"2019-06-01T13:35:23\"}";
    private final TransferRequest REQUEST = new TransferRequest(30005432L, 30005678L, new Money(10.0D), "description", TEST_TIME);
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJsonUsingJackson() throws IOException {
        TransferRequest readRequest = mapper.readValue(JSON, TransferRequest.class);
        assertThat(readRequest).isEqualTo(REQUEST);
    }

    @Test
    void canWriteToJsonWithJackson() throws JsonProcessingException, JSONException {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String writtenJson = mapper.writeValueAsString(REQUEST);
        assertEquals(JSON, writtenJson, JSONCompareMode.STRICT);
    }

}
