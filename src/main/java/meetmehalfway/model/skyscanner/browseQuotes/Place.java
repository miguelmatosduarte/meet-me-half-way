
package meetmehalfway.model.skyscanner.browseQuotes;

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
@JsonDeserialize(builder = Place.PlaceBuilder.class)
@Builder(builderClassName = "PlaceBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "PlaceId",
    "Type",
    "CityId",
    "CountryName",
    "IataCode",
    "SkyscannerCode",
    "CityName",
    "Name"
})
public class Place {

    @JsonProperty("PlaceId")
    private Integer placeId;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("CityId")
    private String cityId;
    @JsonProperty("CountryName")
    private String countryName;
    @JsonProperty("IataCode")
    private String iataCode;
    @JsonProperty("SkyscannerCode")
    private String skyscannerCode;
    @JsonProperty("CityName")
    private String cityName;
    @JsonProperty("Name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PlaceBuilder {
    }
}
