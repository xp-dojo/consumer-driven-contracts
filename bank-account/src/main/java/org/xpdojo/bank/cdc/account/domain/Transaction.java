package org.xpdojo.bank.cdc.account.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
    private final LocalDateTime dateTime;
    private final String description;

    public Transaction(@JsonProperty(value = "amount", required = true) final Money amount,
                       @JsonProperty(value = "direction", required = true) final Direction direction,
                       @JsonProperty(value = "dateTime", required = true) final LocalDateTime dateTime,
                       @JsonProperty(value = "description", required = true) final String description) {
        this.amount = amount;
        this.direction = direction;
        this.dateTime = dateTime;
        this.description = description;
    }

    public static Transaction anOpeningBalanceOf(Money anAmount, LocalDateTime date) {
        return new Transaction(anAmount, CREDIT, date, "Opening balance");
    }

    public static Transaction aDepositOf(Money anAmount, LocalDateTime date, String description) {
        return new Transaction(anAmount, CREDIT, date, description);
    }

    public static Transaction aWithDrawlOf(Money anAmount, LocalDateTime date, String description) {
        return new Transaction(anAmount, DEBIT, date, description);
    }

    Direction direction() {
        return direction;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(amount, that.amount) &&
                direction == that.direction &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, direction, dateTime, description);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", direction=" + direction +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                '}';
    }
}


