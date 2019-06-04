package org.xpdojo.bank.cdc.account.endpoint;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xpdojo.bank.cdc.account.domain.Account;
import org.xpdojo.bank.cdc.account.domain.BalanceResponse;
import org.xpdojo.bank.cdc.account.domain.Transaction;
import org.xpdojo.bank.cdc.account.domain.TransactionResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.xpdojo.bank.cdc.account.domain.Money.anAmountOf;
import static org.xpdojo.bank.cdc.account.domain.Transaction.Direction.CREDIT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceEndpointTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createsAccounts() {
        Account account = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .post("/accounts")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(account.getAccountNumber()).isGreaterThan(0L);
        assertThat(account.getTransactions().size()).isEqualTo(0);
    }

    @Test
    void readsAccounts() {
        List<Account> accounts = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts")
                .then().log().all()
                .extract().body()
                .jsonPath().getList(".", Account.class);
        assertThat(accounts.size()).isEqualTo(3);
    }

    @Test
    void retrievesAccountsById() {
        Account account = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("accounts/30001234")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(account.getAccountNumber()).isEqualTo(30001234L);
    }

    @Test
    void retrievesTransactionsFromAccounts() {
        List<Transaction> transactions = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts/30001234/transactions")
                .then().log().all()
                .extract().body()
                .jsonPath().getList(".", Transaction.class);
        assertThat(transactions.size()).isEqualTo(2);
    }

    @Test
    void updatesAccountsWithTransactions() {
        TransactionResponse response = given()
                .header("Content-Type", "application/json")
                .body(new Transaction(anAmountOf(100.0d), CREDIT, now()))
                .when().log().all()
                .post("/accounts/30005678/transactions")
                .then().log().all()
                .extract().body().as(TransactionResponse.class);
        assertThat(response.getBalance()).isEqualTo(anAmountOf(220.0D));
    }

    @Test
    void getBalanceForAccount() {
        BalanceResponse response = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts/30001234/balance")
                .then().log().all()
                .extract().body().as(BalanceResponse.class);
        assertThat(response.getAccountNumber()).isEqualTo(30001234L);
        assertThat(response.getBalance()).isEqualTo(anAmountOf(-900.0D));
    }

    @Test
    void createAndUpdateAccountEndToEndTest() {
        Account account = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .post("/accounts")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(account.getAccountNumber()).isGreaterThan(0L);
        assertThat(account.getTransactions().size()).isEqualTo(0);

        TransactionResponse response = given()
                .header("Content-Type", "application/json")
                .body(new Transaction(anAmountOf(30.0d), CREDIT, now()))
                .when().log().all()
                .post("/accounts/" + account.getAccountNumber() + "/transactions")
                .then().log().all()
                .extract().body().as(TransactionResponse.class);
        assertThat(response.getBalance()).isEqualTo(anAmountOf(30.0d));
    }

}