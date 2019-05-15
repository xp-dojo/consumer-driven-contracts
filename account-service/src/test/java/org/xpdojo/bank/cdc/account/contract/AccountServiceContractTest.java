package org.xpdojo.bank.cdc.account.contract;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@PactFolder("pacts")
@Provider("account_provider")
public class AccountServiceContractTest {

    private static final Logger LOG = getLogger(AccountServiceContractTest.class);

    @TestTarget
    public Target target;

    @LocalServerPort
    public void setLocalServerPort(int localPort) {
        target = new HttpTarget("http", "localhost", localPort, "/");
    }

    @State("Account with AccountNumber 2468 exists")
    public void toGetState() {
        LOG.info("Checking that account with account number #1234 exists");
    }

}
