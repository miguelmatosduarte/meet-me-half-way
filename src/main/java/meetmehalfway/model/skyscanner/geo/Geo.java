
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
import java.util.stream.Collectors;

@Data
@JsonDeserialize(builder = Geo.GeoBuilder.class)
@Builder(builderClassName = "GeoBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Continents"
})
public class Geo {

    @JsonProperty("Continents")
    private List<Continent> continents;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    public List<City> getCities(){
        return this.continents.stream()
                .map(Continent::getCountries)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .stream()
                .map(Country::getCities)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                ;
    }

    public City fromCityId(String cityID){
        return getCities()
                .stream()
                .filter(c -> c.getId().equals(cityID))
                .collect(Collectors.toList())
                .get(0)
                ;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class GeoBuilder {
    }
}
