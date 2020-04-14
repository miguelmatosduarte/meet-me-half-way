
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
@JsonDeserialize(builder = City.CityBuilder.class)
@Builder(builderClassName = "CityBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "SingleAirportCity",
    "Airports",
    "CountryId",
    "Location",
    "IataCode",
    "Id",
    "Name"
})
public class City {

    @JsonProperty("SingleAirportCity")
    private Boolean singleAirportCity;
    @JsonProperty("Airports")
    private List<Airport> airports;
    @JsonProperty("CountryId")
    private String countryId;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("IataCode")
    private String iataCode;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    private String countryName;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CityBuilder {
    }
}
