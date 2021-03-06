
package meetmehalfway.model.api.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import meetmehalfway.model.skyscanner.validationErrors.ValidationErrors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonDeserialize(builder = Result.ResultBuilder.class)
@Builder(builderClassName = "ResultBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "errors",
        "searchResult"
})
public class Result {

    @JsonProperty("type")
    private String type;
    @JsonProperty("errors")
    private List<Error> errors;
    @JsonProperty("searchResult")
    private List<SearchResult> searchResult;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResultBuilder {
    }
}
