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

public class TransferRequestMapperTest {

    private static final String JSON = "{\"fromAccount\":30005432,\"toAccount\":30005678,\"amount\":{\"value\":10.0}}";
    private static final TransferRequest REQUEST = new TransferRequest(30005432L, 30005678L, new Money(10.0D));
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJson() throws IOException {
        TransferRequest readRequest = mapper.readValue(JSON, TransferRequest.class);
        assertThat(readRequest).isEqualTo(REQUEST);
    }

    @Test
    void canWriteToJson() throws JsonProcessingException, JSONException {
        String writtenJson = mapper.writeValueAsString(REQUEST);
        assertEquals(writtenJson, JSON, JSONCompareMode.STRICT);
    }
}
