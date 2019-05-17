package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class WithdrawlResponse {

    private String response = "no response";

    public WithdrawlResponse(){

    }

    public WithdrawlResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
