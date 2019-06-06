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

[What are they supposed to be doing?]

## Architecture Overview

![](architecture.png)

## Consumer Driven Contracts

The API's services use to call each other contractual. If your application is expecting a certain response when it makes a HTTP `GET`, any change to the server providing that 

Defining a "contract" between services


# Additional Reading

[Roy Fielding's orginal discussion of RESTful architecture](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm)  
