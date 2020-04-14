
package meetmehalfway.model.skyscanner.geo;

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
@JsonDeserialize(builder = Airport.AirportBuilder.class)
@Builder(builderClassName = "AirportBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CityId",
    "CountryId",
    "Location",
    "Id",
    "Name"
})
public class Airport {

    @JsonProperty("CityId")
    private String cityId;
    @JsonProperty("CountryId")
    private String countryId;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AirportBuilder {
    }
}
