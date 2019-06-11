package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDataMapperTest {

    private static final String JSON = "{\"accountNumber\":30001234,\"description\":\"A description\",\"overdraftFacility\":{\"value\":5000.0},\"balance\":{\"value\":-1000.0}}";
    private static final AccountData ACCOUNT_DATA = new AccountData(30001234L, "A description", new Amount(5000.0D), new Amount(-1000.0D));
    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Test
    void canBeCreatedFromJson() throws IOException {
        AccountData readAccountData = mapper.readValue(JSON, AccountData.class);
        assertThat(readAccountData).isEqualTo(ACCOUNT_DATA);
    }
}
