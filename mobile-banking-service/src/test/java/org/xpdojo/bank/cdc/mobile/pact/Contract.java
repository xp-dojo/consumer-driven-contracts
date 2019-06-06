package org.xpdojo.bank.cdc.mobile.pact;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

import java.util.HashMap;
import java.util.Map;

interface Contract {

	static RequestResponsePact accountBalanceContract(PactDslWithProvider builder) {
		return builder
			.uponReceiving("Request for all accounts")
			.path("/accounts/30002468/balance")
			.method("GET")
			.willRespondWith()
			.status(200)
			.headers(expectedHeaders())
			.body(expectedAccountsBody())
			.toPact();
	}

	private static Map<String, String> expectedHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		return headers;
	}

	private static PactDslJsonBody expectedAccountsBody() {
		return new PactDslJsonBody()
			.id("accountNumber")
			.stringType("description")
			.object("overdraftFacility", valueObject())
			.object("balance", valueObject())
			.asBody();
	}

	private static DslPart valueObject() {
		return new PactDslJsonBody().decimalType("value");
	}

}
