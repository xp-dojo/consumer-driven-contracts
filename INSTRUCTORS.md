## Consumer Driven Contracts
Using the [Pact](https://docs.pact.io/) framework.

### Notes
Things to draw out in the presentation:
 - services are not compiled against other services.  We only know if they work at runtime.
 - contracts allow us to be clear about the data in the payloads, as defined by the consumers
 - using frameworks like Pact we can create a set of test criteria for both producers and consumers to adhere to 
 without coupled tests
 
#### As a consumer
1. We do not want to have to run our tests against the real endpoints that we depend on.  CI envs will be 
constantly failing.
1. We need a way to decouple the consumer tests from the provider implementation.  We use a Pact test for this.
1. The Pact tests will do a few things for us.
    1. They create a mock provider service that supplies us with the minimum set of attributes that we need.  We make the 
    assumption that as long as the minimum set of things is there, then we do not care if additional attributes exist.
    1. Pact does this behind the scenes and injects the server into the test 
    `@Test void checkWeCanProcessTheAccountData(MockServer mockProvider)`
    1. The server is preset from the method annotated with `@Pact(provider = "account_provider", consumer = "atm_consumer")`
    1. The actual test will call the `mockServer` and use the response as it would in your real application.  You should 
    create your real domain objects from the pre-programmed response from the mock server.
    1. Once your test completes, Pact will create a Pact JSON file in the pacts directory (see output of the test)
1. As a consumer, you now give this Pact to the data provider.  This is the Consumer Driven Contract that the provider 
should adhere to.  The provider should continuously test these pacts, opening up a conversation with any consumer where 
provider test fail.

## As a service provider
1. We want to know who our consumer are and what data attributes they need.
1. We want to know that if we have to change the contract who we need to work with to change the APIs.

## How to do it incorrectly
1. We need to remember that the contract tests are just that, they are not about the actual data ... they are about the shape of the data
1. These tests are as solid as their use.  What we mean by this is if only a proportion of consumers use the contract tests then the end to end 