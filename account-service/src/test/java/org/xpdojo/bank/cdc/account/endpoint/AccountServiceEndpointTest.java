package org.xpdojo.bank.cdc.account.endpoint;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xpdojo.bank.cdc.account.domain.*;

import java.time.LocalDateTime;
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
    void readsAccounts() {
        List<AccountSummary> accounts = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts")
                .then().log().all()
                .extract().body()
                .jsonPath().getList(".", AccountSummary.class);
        assertThat(accounts.size()).isEqualTo(5);
    }

    @Test
    void retrievesAccountsById() {
        AccountSummary account = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("accounts/30001234/balance")
                .then().log().all()
                .extract().body().as(AccountSummary.class);
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
        assertThat(transactions.size()).isEqualTo(5);
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
    void transfersBetweenAccounts() {
        TransferResponse response = given()
                .header("Content-Type", "application/json")
                .body(new TransferRequest(30009876L, 30005432L, anAmountOf(100.0D), LocalDateTime.now()))
                .when().log().all()
                .post("/accounts/transfers")
                .then().log().all()
                .extract().body().as(TransferResponse.class);
        assertThat(response.getResponse()).isNotEmpty();
    }

    @Test
    void getBalanceForAccount() {
        AccountSummary response = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts/30001234/balance")
                .then().log().all()
                .extract().body().as(AccountSummary.class);
        assertThat(response.getAccountNumber()).isEqualTo(30001234L);
        assertThat(response.getBalance()).isEqualTo(anAmountOf(-900.0D));
    }

}