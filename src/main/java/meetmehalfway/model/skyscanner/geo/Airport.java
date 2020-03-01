
package meetmehalfway.model.skyscanner.geo;

import java.util.HashMap;
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
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("CityId")
    public String getCityId() {
        return cityId;
    }

    @JsonProperty("CityId")
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Airport withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    @JsonProperty("CountryId")
    public String getCountryId() {
        return countryId;
    }

    @JsonProperty("CountryId")
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public Airport withCountryId(String countryId) {
        this.countryId = countryId;
        return this;
    }

    @JsonProperty("Location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("Location")
    public void setLocation(String location) {
        this.location = location;
    }

    public Airport withLocation(String location) {
        this.location = location;
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

    public Airport withId(String id) {
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

    public Airport withName(String name) {
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

    public Airport withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cityId).append(countryId).append(location).append(id).append(name).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Airport)) {
            return false;
        }
        Airport rhs = ((Airport) other);
        return new EqualsBuilder().append(cityId, rhs.cityId).append(countryId, rhs.countryId).append(location, rhs.location).append(id, rhs.id).append(name, rhs.name).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
