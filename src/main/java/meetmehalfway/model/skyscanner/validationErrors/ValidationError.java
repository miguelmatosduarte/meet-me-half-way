
package meetmehalfway.model.skyscanner.validationErrors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@JsonDeserialize(builder = ValidationError.ValidationErrorBuilder.class)
@Builder(builderClassName = "ValidationErrorBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ParameterValue",
        "Message",
        "ParameterName"
})
public class ValidationError {

    @JsonProperty("ParameterValue")
    private String parameterValue;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ParameterName")
    private String parameterName;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ValidationErrorBuilder {
    }
}
