package org.xpdojo.bank.cdc.mobile.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountMapperTest {

    private static final String JSON = "{\"accountNumber\":30005432,\"overdraftFacility\":{\"value\":1000.0},\"balance\":{\"value\":90.0}}";
    private static final Account ACCOUNT = new Account(30005432L, new Money(1000.0D), new Money(90.0D));
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJsonUsingJackson() throws IOException {
        Account readAccount = mapper.readValue(JSON, Account.class);
        assertThat(readAccount).isEqualTo(ACCOUNT);
    }
}
