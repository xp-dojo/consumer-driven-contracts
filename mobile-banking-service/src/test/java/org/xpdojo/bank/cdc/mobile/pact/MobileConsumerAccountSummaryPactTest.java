package org.xpdojo.bank.cdc.mobile.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.xpdojo.bank.cdc.mobile.domain.Account;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "account_provider")
public class MobileConsumerAccountSummaryPactTest {

	private static final Logger LOG = LoggerFactory.getLogger(MobileConsumerAccountSummaryPactTest.class);

	@Pact(provider = "account_provider", consumer = "mobile_consumer")
	public RequestResponsePact getContract(PactDslWithProvider builder) {
		return Contract.accountBalanceContract(builder);
	}

	@Test
	void checkWeCanProcessTheAccountData(MockServer mockProvider) throws IOException {
		ResponseEntity<String> response = retrieveAccountData(mockProvider);

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getHeaders().get("Content-Type")).contains("application/json");
		assertThat(response.getBody()).as("Response body from the accounts service is expected to be populated").isNotEmpty();

		LOG.info(response.getBody());

		Account accountData = Jackson2ObjectMapperBuilder.json().build().readValue(response.getBody(), Account.class);
		assertThat(accountData.getAccountNumber()).isNotZero();
		assertThat(accountData.getDescription()).isNotEmpty();
		assertThat(accountData.getOverdraftFacility()).isNotZero();
		assertThat(accountData.getBalance()).isNotZero();
	}

	private ResponseEntity<String> retrieveAccountData(MockServer mockProvider) {
		return new RestTemplate().getForEntity(mockProvider.getUrl() + "/accounts/30002468/balance", String.class);
	}

}