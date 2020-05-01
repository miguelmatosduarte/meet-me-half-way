
package meetmehalfway.model.skyscanner.validationErrors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonDeserialize(builder = ValidationErrors.ValidationErrorsBuilder.class)
@Builder(builderClassName = "ValidationErrorsBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ValidationErrors"
})
public class ValidationErrors {

    @JsonProperty("ValidationErrors")
    private List<ValidationError> validationErrors;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ValidationErrorsBuilder {
    }
}
