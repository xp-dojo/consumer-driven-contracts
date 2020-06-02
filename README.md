# Working with Consumer Driven Contracts

In this workshop you'll be using [contract testing](http://pact.io) to explore:

 * how to ensure the APIs you depend on don't change and accidentally break your applications
 * how to safeguard against accidentally breaking other peoples applications that rely on your APIs
 * how contract testing and other techniques can work together to give you confidence when working with external APIs
 * how contract testing allows you to develop your application confidently without the explicit need for continual integration testing
 * when contract testing can't be employed and when alternative techniques over other advantages

Although we'll work mainly with RESTful style APIs, the principles apply whenever some kind of API dependency exists (for example, depending on a distributed binary, a wire protocol or traditional RPC style APIs).


# Prerequisites

You will need Java and an IDE (we prefer Intellij IDEA). 

The project uses [Gradle](https://gradle.org/), you will either need to work with Gradle from the terminal or, in the case of IntelliJ IDEA, use the Gradle plugin. 

As we use Gradle, performing the steps below before the session will save time downloading various dependencies.


1. Clone the repository with the following command.

   `git clone https://github.com/xp-dojo/consumer-driven-contracts.git`
  
   If you have problems with SSL, you can try the following.
   
   `git clone -c http.sslVerify=false https://github.com/xp-dojo/consumer-driven-contracts.git`
   
   If you have problems with a proxy, you can `unset http_proxy` and `unset https_proxy` (or equivalent for your OS).

1. Open the project from IntelliJ IDEA (community edition is fine). 

   If you have the Gradle plugin installed, things should "just work". Gradle will download all the dependencies and you will see the project compile. Your millage may vary.

1. To test everything is compiling, navigate to `AccountService.java` (under the `account-service` folder) and run it as an application.

   You should see a green run icon to the left of the class declaration. If you don't or can't run it, speak to an instructor.


# Instructions

This session is broken down into four core parts:

1. Getting to know the system
1. Using a contract test to define consumption of an existing data attribute
1. Driving the addition of a new data attribute from the provider using a contract test
1. Something to take away


## Part 1: Getting to know the system

The project is split up into the following folders.

| Folder                   | Description              | Location |
|--------------------------|--------------------------|-----------|
| `account-service`        | Central banking platform | http://localhost:8901 |
| `atm-service`            | ATM application          | http://localhost:8902 |
| `mobile-banking-service` | Mobile application       | http://localhost:8903 |
| `bank-account`           | Banking library          | - |
| `discovery-service`      | Discovery services       | http://localhost:8761 |

The components and their interactions are shown below.

![](architecture.png)

In this part of the session, we would like you to **start all of the services** in the follow sequence (right click on the Java file, select `Run ...main()`):

1. The [discovery-service](http://localhost:8761) (`DiscoveryService.java`)
1. The [account-service](http://localhost:8902) (`AccountService.java`)
1. The [atm-service](http://localhost:8902) (`AtmService.java`)
1. The [mobile-service](http://localhost:8903) (`MobileBankingService.java`)

> For that authentic "mobile" experience, when viewing the mobile app, [change the viewport of your browser](https://superuser.com/questions/1214829/how-can-i-view-the-mobile-version-of-a-webpage-in-google-chrome-for-desktop) to a mobile device.

Have a play with them withdrawing money from the ATM and transferring monies in the mobile application. If you have any problems ask one of us in the room to help you.

Now have a look at the `account-service` swagger API as it will tell you a lot about what you can do in that service. The swagger UI can be found at http://localhost:8901/swagger-ui.html#/bank-account-endpoint. Use the Swagger UI to `GET` all the accounts.

You now have a good idea about what the application architecture does, we can now change it a little.


## Part 2: Using a contract test to define consumption of an existing data attribute

### First we update the consumer contract

1. We want to include an account's description along with the account summary information in mobile app. Currently the Mobile app does not use this data attribute.

   There is a `description` attribute on the `AccountSummary`; it's returned by the `account-service` (producer) but is not currently used by any client (consumer). We'd like to include it in mobile application and ensure it's use is formalised in a "contract". Add the `description` attribute to the domain object.

   Add an **assertion** in the mobile app's _contract_ to verify the description field is valid. Run the test. **Hint:** look in `MobileConsumerAccountSummaryPactTest.java`. Ensure you have as assertion along the lines of `assertThat(account.getDescription()).isNotEmpty()`, then use the IDE to create the method and attribute in the domain object. **Don't add the description to the mock server yet.**

1. You should see the test fails until you simulate the server sending back the description.

   Add the description field to the expected response in the contract (`MobileConsumerAccountSummaryPactTest`). Re-run the test and see it pass.
 
   > This is simulating the server sending back an additional JSON field. Have a think how you could test, if it is returning it already.
 
1. Remove or comment out the `account-service` code that returns the description. What happens in the mobile application when you restart the service?

1. Display the description in the mobile app. **Hint:** look in `accountSummaryView.html` and `accountListView.html`.

1. You have now re-defined what you expect from the producer. See the logs from running the consumer test. You should see `Writing pact mobile_consumer -> account_provider to file target/pacts/mobile_consumer-account_provider.json`. This is the contract.

   Open it up and have a look at the contents.

> If we were to give the contract (JSON) to the producer they will know how we are using their API - this is the _contract exchange_.


### Then we give the contract to the Producer

We now need to give the new pact contract to the producer. 

1. First lets just check that the existing version of the pact works OK in the account-service: run the `AccountServiceContractTest`. 
1. Copy the newly created JSON pact file from the consumer (_mobile_consumer-account_provider.json_) into the `src/test/resources/pact` directory of the account service (there is already an older version there so you are copying over the top of the old one). If you use `git diff` you can see the changes you have introduced. Its worth noting that the addition of one attribute adds both the attribute and the matching rules.
1. Now run the `AccountServiceContractTest`. You can see from the annotations that it starts an instance of the accounts-service and tests the pact against it.

### Finish off
We now need to make sure it all hangs together
1. Restart the Mobile service and check that you can see the account description in the mobile app
1. Did any other tests fail (there will be a few)?  Run `gradle` from the command line and check we are __done__

__A great place for a commit!__

  You should see a failure indicating that _known_ clients are relying on a _contract_ you no longer respect.

## Part 3: Driving the addition of a new attribute from the data provider using a contract test

For this part of the session we are going to drive an end to end change into the services.  We will __drive__ the addition of a description to transactions. You can see the current set of transactions if you view the transactions for account number 30001234 [here](http://localhost:8901/accounts/30001234/transactions).

Now you are experienced with contract testing we would like you to:

1. Update the ATM contract (see `AtmConsumerWithdrawlPactTest`) so it expects to see the additional description attribute in the `WithdrawlRequest` domain object. You should update the domain object too. Remember in this instance it's the `POST` body that we are defining. Generate the new ATM pact contract.
> you may wish to hardcode a description rather than reconstructing the ATM machine (for example "ATM Withdrawal")
1. Replace the ATM pact contract in the `account-service` and make the pact provider test pass by implementing the additional attribute.
1. Now lets do the same thing for the Mobile service.
Use the same URL above to see the additional attribute in the accounts repository.


## Part 4: Something to take away

You will notice in the `mobile-service` that you can view an account summary ... it would be nice to see the transactions, don't you think?


# Background

## Consumer Driven Contracts

All API's are contractual; they define how to make calls and what to expect in return. Formalising these contracts into some kind of external specification allows us to test consumers and producers of these APIs. There are lots of techniques we can use to do this, the previous session on [ATDD](https://github.com/xp-dojo/atdd-bank-account) is one example.

Consumer driven contracts or _contract testing_ is another technique whereby auto-generated "contracts" are executed against consumers and producers to ensure neither deviate.


## Architecture Overview

We will be continuing the Bank Account theme and have provided **three applications** and one **library jar**. 

 * The **central banking platform** uses the **bank account library** to manage a single user's accounts
 * The **Mobile application** allows user's to interact with thier accounts from their mobile device
 * The **ATM application** is installed on ATM branches and allows users to physically withdraw money and perform basic banking tasks


# Additional Reading

[Pack.io](https://docs.pact.io/) has lots of interesting background and useful information  
[Roy Fielding's orginal discussion of the RESTful architecture](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm)   


