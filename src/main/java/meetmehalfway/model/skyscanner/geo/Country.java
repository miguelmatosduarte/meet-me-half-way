
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
@JsonDeserialize(builder = Country.CountryBuilder.class)
@Builder(builderClassName = "CountryBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CurrencyId",
    "Regions",
    "Cities",
        "Id",
        "Name"
})
public class Country {

    @JsonProperty("CurrencyId")
    private String currencyId;
    @JsonProperty("Regions")
    private List<Object> regions;
    @JsonProperty("Cities")
    private List<City> cities;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CountryBuilder {
    }
}
