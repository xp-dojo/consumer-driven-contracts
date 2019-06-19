package org.xpdojo.bank.cdc.atm.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.xpdojo.bank.cdc.atm.domain.Amount;
import org.xpdojo.bank.cdc.atm.domain.WithdrawalRequest;
import org.xpdojo.bank.cdc.atm.domain.WithdrawalResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "account_provider")
public class AtmConsumerWithdrawalPactTest {

    @Pact(provider = "account_provider", consumer = "atm_consumer")
    public RequestResponsePact configureMockServer(PactDslWithProvider builder) {
        return builder
                .given("Account with AccountNumber 30002468 exists")
                .uponReceiving("POST REQUEST with a transaction")
                .method("POST")
                .body(expectedWithdrawalBody())
                .path("/accounts/30002468/transactions")
                .willRespondWith()
                .status(201)
                .headers(expectedHeaders())
                .body(dummyResponse())
                .toPact();
    }

    private Map<String, String> expectedHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private PactDslJsonBody expectedWithdrawalBody() {
        return new PactDslJsonBody()
                .id("accountNumber", 30002468L)
                .object("amount", expectAmountValue(100.0D))
                .stringValue("direction", "DEBIT")
                .date("dateTime", "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
                .asBody();
    }

    private PactDslJsonBody dummyResponse() {
        return new PactDslJsonBody()
                .id("accountNumber", 30002468L)
                .stringType("response")
                .object("balance", expectAmountValue(900.0D))
                .asBody();
    }

    private DslPart expectAmountValue(final double amount) {
        return new PactDslJsonBody()
                .decimalType("value", amount);
    }

    @Test
    void checkWeCanPostToTheServer(MockServer server) {
        WithdrawalRequest request = new WithdrawalRequest(30002468L, new Amount(100D), LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<WithdrawalRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<WithdrawalResponse> postResponse = makeHttpPostTo("/accounts/30002468/transactions", entity, server);

        assertThat(postResponse.getStatusCode().value()).isEqualTo(201);

        assertThat(postResponse.getBody().getAccountNumber()).isEqualTo(30002468L);
        assertThat(postResponse.getBody().getResponse()).isNotEmpty();
        assertThat(postResponse.getBody().getBalance()).isEqualTo(new Amount(900.0D));
    }

    private ResponseEntity<WithdrawalResponse> makeHttpPostTo(String endPoint, HttpEntity entity, MockServer server) {
        return new RestTemplate().postForEntity(server.getUrl() + endPoint, entity, WithdrawalResponse.class);
    }

}
