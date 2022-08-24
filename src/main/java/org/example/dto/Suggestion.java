package org.example.dto;

import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value",
        "unrestricted_value",
        "data"
})
@Generated("jsonschema2pojo")
public class Suggestion {

    @JsonProperty("value")
    public String value;
    @JsonProperty("unrestricted_value")
    public String unrestrictedValue;
    @JsonProperty("data")
    @Valid
    public Data data;

    @Override
    public String toString() {
        return "Suggestion{" +
                "value='" + value + '\'' +
                ", unrestrictedValue='" + unrestrictedValue + '\'' +
                ", data=" + data +
                '}';
    }
}