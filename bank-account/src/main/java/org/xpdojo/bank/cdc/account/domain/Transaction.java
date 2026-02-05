package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static org.xpdojo.bank.cdc.account.domain.Transaction.Direction.CREDIT;
import static org.xpdojo.bank.cdc.account.domain.Transaction.Direction.DEBIT;

@JsonAutoDetect(fieldVisibility = ANY)
public class Transaction {
    private final Money amount;
    private final Direction direction;
    private final String description;
    private final LocalDateTime dateTime;

    public Transaction(@JsonProperty(value = "amount", required = true) Money amount,
                       @JsonProperty(value = "direction", required = true) Direction direction,
                       @JsonProperty(value = "description", required = true) String description,
                       @JsonProperty(value = "dateTime", required = true) LocalDateTime dateTime) {
        this.amount = amount;
        this.direction = direction;
        this.description = description;
        this.dateTime = dateTime;
    }

    public static Transaction anOpeningBalanceOf(Money anAmount, LocalDateTime date) {
        return new Transaction(anAmount, CREDIT, "Opening Balance",date);
    }

    public static Transaction aDepositOf(Money anAmount, String description, LocalDateTime date) {
        return new Transaction(anAmount, CREDIT, description, date);
    }

    public static Transaction aWithDrawlOf(Money anAmount, String description,LocalDateTime date) {
        return new Transaction(anAmount, DEBIT, description, date);
    }

    Direction direction() {
        return direction;
    }

    public String getDescription() {
        return description;
    }

    Money amount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    Money balanceImpact() {
        if (direction == DEBIT)
            return amount.negative();
        else
            return amount;
    }

    public enum Direction {
        DEBIT, CREDIT
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(amount, that.amount) && direction == that.direction && Objects.equals(description, that.description) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, direction, description, dateTime);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", direction=" + direction +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}


