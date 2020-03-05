
package meetmehalfway.model.skyscanner.geo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<Object> regions = new ArrayList<>();
    @JsonProperty("Cities")
    private List<City> cities = new ArrayList<>();
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("CurrencyId")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("CurrencyId")
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Country withCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    @JsonProperty("Regions")
    public List<Object> getRegions() {
        return regions;
    }

    @JsonProperty("Regions")
    public void setRegions(List<Object> regions) {
        this.regions = regions;
    }

    public Country withRegions(List<Object> regions) {
        this.regions = regions;
        return this;
    }

    @JsonProperty("Cities")
    public List<City> getCities() {
        return cities;
    }

    @JsonProperty("Cities")
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Country withCities(List<City> cities) {
        this.cities = cities;
        return this;
    }

    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    public Country withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public Country withName(String name) {
        this.name = name;
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

    public Country withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(currencyId).append(regions).append(cities).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Country)) {
            return false;
        }
        Country rhs = ((Country) other);
        return new EqualsBuilder().append(currencyId, rhs.currencyId).append(regions, rhs.regions).append(cities, rhs.cities).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
