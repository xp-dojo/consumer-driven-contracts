package org.xpdojo.bank.cdc.atm.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class Amount {
    final Double value;

    public Amount(@JsonProperty("value") final Double value) {
        this.value = value;
    }

}
