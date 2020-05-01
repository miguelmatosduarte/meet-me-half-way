
package meetmehalfway.model.api.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import meetmehalfway.model.skyscanner.SkyScannerApiResponse;
import meetmehalfway.utils.ErrorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonDeserialize(builder = Error.ErrorBuilder.class)
@Builder(builderClassName = "ErrorBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "message"
})
public class Error {

    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonIgnore
    private Map<String, Object> additionalProperties;
    @JsonPOJOBuilder(withPrefix = "")
    public static class ErrorBuilder {
    }

    public static List<Error> parseValidationErrors(List<SkyScannerApiResponse> validationErrors){
        return  validationErrors
                .stream()
                .filter(r -> r.getValidationErrors().getValidationErrors() != null)
                .map(
                        r ->
                            r.getValidationErrors().getValidationErrors()
                                    .stream()
                                    .map(e -> Error.builder()
                                    .code(ErrorType.SKYSCANNER_VALIDATION_ERROR.getCode())
                                            .message(
                                                    String.format("Passenger %d - %s %s - %s .",
                                                            r.getPassengerNumber() + 1,
                                                            ErrorType.SKYSCANNER_VALIDATION_ERROR.getDescription(),
                                                            e.getParameterName(),
                                                            e.getMessage()
                                                    )
                                            )
                                            .build()
                                    )
                                    .collect(Collectors.toList())
                )
                .collect(ArrayList::new, List::addAll, List::addAll);
    }
}
