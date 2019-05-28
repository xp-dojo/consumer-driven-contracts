package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithdrawalResponse {

    @JsonProperty("response")
    private String response;
    @JsonProperty("resultingBalance")
    private Amount balance;

    public WithdrawalResponse(){

    }

    public WithdrawalResponse(String response, Amount balance) {
        this.response = response;
        this.balance = balance;
    }

    public String getResponse() {
        return response;
    }

    public Amount getBalance() {
        return balance;
    }

}
