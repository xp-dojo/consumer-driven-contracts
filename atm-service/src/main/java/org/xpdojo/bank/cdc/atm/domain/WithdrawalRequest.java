package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.datetime.DateFormatter;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.apache.commons.lang.time.DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WithdrawalRequest {

    private final Long accountNumber;
    private final Amount amount;
    private final String direction = "DEBIT";
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;

    public WithdrawalRequest(@JsonProperty("accountNumber") final Long accountNumber,
                             @JsonProperty("amount") final Amount amount,
                             @JsonProperty("date") final LocalDateTime date) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.date = date;
    }

    public Amount getAmount() {
        return amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getDirection() {
        return direction;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawalRequest that = (WithdrawalRequest) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(direction, that.direction) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amount, direction, date);
    }

    @Override
    public String toString() {
        return "WithdrawalRequest{" +
                "accountNumber=" + accountNumber +
                ", amount=" + amount +
                ", direction='" + direction + '\'' +
                ", date=" + date +
                '}';
    }
}
