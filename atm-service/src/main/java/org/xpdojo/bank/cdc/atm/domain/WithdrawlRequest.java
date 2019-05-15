package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class WithdrawlRequest {

    private final Amount amount;
    private final String direction = "DEBIT";
    private final LocalDateTime date = LocalDateTime.now();

    public WithdrawlRequest(final Amount amount) {
        this.amount = amount;
    }

}
