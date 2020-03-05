
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
    private List<Airport> airports = new ArrayList<>();
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
    private Map<String, Object> additionalProperties = new HashMap<>();

    private String countryName;

    @JsonProperty("SingleAirportCity")
    public Boolean getSingleAirportCity() {
        return singleAirportCity;
    }

    @JsonProperty("SingleAirportCity")
    public void setSingleAirportCity(Boolean singleAirportCity) {
        this.singleAirportCity = singleAirportCity;
    }

    public City withSingleAirportCity(Boolean singleAirportCity) {
        this.singleAirportCity = singleAirportCity;
        return this;
    }

    @JsonProperty("Airports")
    public List<Airport> getAirports() {
        return airports;
    }

    @JsonProperty("Airports")
    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public City withAirports(List<Airport> airports) {
        this.airports = airports;
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

    public City withCountryId(String countryId) {
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

    public City withLocation(String location) {
        this.location = location;
        return this;
    }

    @JsonProperty("IataCode")
    public String getIataCode() {
        return iataCode;
    }

    @JsonProperty("IataCode")
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public City withIataCode(String iataCode) {
        this.iataCode = iataCode;
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

    public City withId(String id) {
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

    public City withName(String name) {
        this.name = name;
        return this;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public City withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(singleAirportCity).append(airports).append(countryId).append(location).append(iataCode).append(id).append(name).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof City)) {
            return false;
        }
        City rhs = ((City) other);
        return new EqualsBuilder().append(singleAirportCity, rhs.singleAirportCity).append(airports, rhs.airports).append(countryId, rhs.countryId).append(location, rhs.location).append(iataCode, rhs.iataCode).append(id, rhs.id).append(name, rhs.name).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
