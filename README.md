# Working with Consumer Driven Contracts

In this workshop you'll be using [contract testing](http://pact.io) to explore:

 * how to ensure the APIs you depend don't accidentally break your applications
 * how to safe guard against accidentally breaking other peoples applications that rely on your APIs
 * how contract testing and other techniques can work together to give you confidence when working with external APIs

Although we'll work mainly with RESTful style APIs, the principles apply whenever some kind of API dependency exists (for example, depending on a distributed binary, a wire protocol or traditional RPC style APIs)


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

> **@Pete** can you sense check this and make sure i've got the right idea. We'll have some time to adjust but should if anything is way off!

1. We want to include an account's description along with the account summary information in mobile app.

   There is a `description` attribute in `Account` and `AccountSummary` but it's not returned by the `account-service` (producer). We'd like to add it.
   
   Add a description to the mobile app's _contract_ and modify the tests to fail when run. This should demonstrate that the API does not yet send back the `description field`.
   
1. Fix the failing test from the previous step. 

1. Display the description in the mobile app. **Hint:** look in `accountSummaryView.html` and `accountListView.html`. 


## Consumer Driven Contracts

All API's are contractual; they define how to make calls and what to expect in return. Formalising these contracts into some kind of external specification allows us to test consumers and producers of these APIs. There are lots of techniques we can use to do this, the previous session on [ATDD](https://github.com/xp-dojo/atdd-bank-account) is one example.

Consumer driven contracts or _contract testing_ is another technique whereby auto-generated "contracts" are executed against consumers and producers to ensure neither deviate.


## Architecture Overview

We will be continuing the Bank Account theme and have provided **three applications** and one **library jar**. 

 * The **central banking platform** uses the **bank account library** to manage a single user's accounts
 * The **Mobile application** allows user's to interact with thier accounts from their mobile device
 * The **ATM application** is installed on ATM branches and allows users to physically withdraw money and perform basic banking tasks
 
 
The project is split up into the following folders.

| Folder                   | Description              |
|--------------------------|--------------------------|
| `banking-service`        | Central banking platform |
| `atm-service`            | ATM application          |
| `mobile-banking-service` | Mobile application       |
| `bank-account`           | Banking library          |
| `discovery-service`      | Discovery services       |


The components and their interactions are shown below.

![](architecture.png)




# Additional Reading

[Roy Fielding's orginal discussion of RESTful architecture](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm)  
