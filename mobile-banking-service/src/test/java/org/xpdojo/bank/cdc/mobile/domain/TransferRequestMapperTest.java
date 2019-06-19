package org.xpdojo.bank.cdc.mobile.domain;

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

public class TransferRequestMapperTest {

    private static final LocalDateTime TEST_TIME = LocalDateTime.of(2019, Month.JUNE, 01, 13, 35, 23);
    private static final String JSON = "{\"fromAccount\":30005432,\"toAccount\":30005678,\"amount\":{\"value\":10},\"dateTime\":\"2019-06-01T13:35:23\",\"description\":\"A description\"}";
    private static final TransferRequest REQUEST = new TransferRequest(30005432L, 30005678L, new Money(10.0D), TEST_TIME, "A description");
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJsonUsingJackson() throws IOException {
        TransferRequest readRequest = mapper.readValue(JSON, TransferRequest.class);
        assertThat(readRequest).isEqualTo(REQUEST);
    }

    @Test
    void canWriteToJsonWithJackson() throws JsonProcessingException, JSONException {
        String writtenJson = mapper.writeValueAsString(REQUEST);
        assertEquals(JSON, writtenJson, JSONCompareMode.STRICT);
    }

}
