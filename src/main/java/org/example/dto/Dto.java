
package org.example.dto;

import java.util.List;
import javax.annotation.Generated;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "suggestions"
})
@Generated("jsonschema2pojo")
public class Dto {

    @JsonProperty("suggestions")
    @Valid
    public List<Suggestion> suggestions = null;

}

