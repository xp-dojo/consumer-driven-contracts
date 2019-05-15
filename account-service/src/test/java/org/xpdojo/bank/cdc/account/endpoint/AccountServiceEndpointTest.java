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
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createsAccounts() {
        Account account = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .post("/accounts")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(account.getAccountNumber()).isGreaterThan(0l);
        assertThat(account.getTransactions().size()).isEqualTo(0);
    }

    @Test
    public void readsAccounts() {
        List<Account> accounts = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts")
                .then().log().all()
                .extract().body()
                .jsonPath().getList(".", Account.class);
        assertThat(accounts.size()).isEqualTo(2);
    }

    @Test
    public void retrievesAccountsById() {
        Account account = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("accounts/1234")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(account.getAccountNumber()).isEqualTo(1234l);
    }

    @Test
    public void retrievesTransactionsFromAccounts() {
        List<Transaction> transactions = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts/1234/transactions")
                .then().log().all()
                .extract().body()
                .jsonPath().getList(".", Transaction.class);
        assertThat(transactions.size()).isEqualTo(2);
    }

    @Test
    public void updatesAccountsWithTransactions() {
        Account account = given()
                .header("Content-Type", "application/json")
                .body(new Transaction(anAmountOf(100.0d), CREDIT, now()))
                .when().log().all()
                .post("/accounts/5678/transactions")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(account.getTransactions().size()).isEqualTo(2);
    }

    @Test
    public void getBalanceForAccount(){
        BalanceResponse response = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get("/accounts/1234/balance")
                .then().log().all()
                .extract().body().as(BalanceResponse.class);
        assertThat(response.getAccountNumber()).isEqualTo(1234L);
        assertThat(response.getBalance()).isEqualTo(anAmountOf(-900.0D));
    }

    @Test
    public void createAndUpdateAccountEndToEndTest() {
        Account account = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .post("/accounts")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(account.getAccountNumber()).isGreaterThan(0l);
        assertThat(account.getTransactions().size()).isEqualTo(0);

        Account updatedAccount = given()
                .header("Content-Type", "application/json")
                .body(new Transaction(anAmountOf(30.0d), CREDIT, now()))
                .when().log().all()
                .post("/accounts/" + account.getAccountNumber() + "/transactions")
                .then().log().all()
                .extract().body().as(Account.class);
        assertThat(updatedAccount.getTransactions().size()).isEqualTo(1);
        assertThat(updatedAccount.balance()).isEqualTo(anAmountOf(30.0d));
    }

}