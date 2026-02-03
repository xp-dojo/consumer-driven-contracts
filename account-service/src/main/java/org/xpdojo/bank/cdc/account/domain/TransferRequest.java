package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class TransferRequest {

    private final Long fromAccount;
    private final Long toAccount;
    private final Money amount;
    private final String description;
    private final LocalDateTime dateTime;

    public TransferRequest(@JsonProperty(value = "fromAccount", required = true) final Long fromAccount,
                           @JsonProperty(value = "toAccount", required = true) final Long toAccount,
                           @JsonProperty(value = "amount", required = true) final Money amount,
                           @JsonProperty(value = "description", required = true) final String description,
                           @JsonProperty(value = "dateTime", required = true) final LocalDateTime dateTime) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
    }

    public Long getFromAccount() {
        return fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public Money getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransferRequest that = (TransferRequest) o;
        return Objects.equals(fromAccount, that.fromAccount) && Objects.equals(toAccount, that.toAccount) && Objects.equals(amount, that.amount) && Objects.equals(description, that.description) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccount, toAccount, amount, description, dateTime);
    }


}
