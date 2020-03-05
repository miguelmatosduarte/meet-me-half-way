
package meetmehalfway.model.skyscanner.geo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Continents"
})
public class Geo {

    @JsonProperty("Continents")
    private List<Continent> continents = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("Continents")
    public List<Continent> getContinents() {
        return continents;
    }

    @JsonProperty("Continents")
    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    public Geo withContinents(List<Continent> continents) {
        this.continents = continents;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Geo withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(continents).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Geo)) {
            return false;
        }
        Geo rhs = ((Geo) other);
        return new EqualsBuilder().append(continents, rhs.continents).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

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

    public City fromCityName(String name){
        return getCities()
                .stream()
                .filter(c -> c.getName().equals(name))
                .collect(Collectors.toList())
                .get(0)
                ;
    }

    public void fillCityCountryName(){
        this.continents.forEach(
                c -> c.getCountries().forEach(
                        coun -> coun.getCities().forEach(
                                city -> city.setCountryName(coun.getName())
                        )
                )
        );
    }
}
