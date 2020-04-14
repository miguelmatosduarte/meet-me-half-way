
package meetmehalfway.model.skyscanner.geo;

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
@JsonDeserialize(builder = Continent.ContinentBuilder.class)
@Builder(builderClassName = "ContinentBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Countries"
})
public class Continent {

    @JsonProperty("Countries")
    private List<Country> countries;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ContinentBuilder {
    }
}
