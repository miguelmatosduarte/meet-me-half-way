
package json.schema.browsequotesresponse;

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
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("PlaceId")
    public Integer getPlaceId() {
        return placeId;
    }

    @JsonProperty("PlaceId")
    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Place withPlaceId(Integer placeId) {
        this.placeId = placeId;
        return this;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    public Place withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("CityId")
    public String getCityId() {
        return cityId;
    }

    @JsonProperty("CityId")
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Place withCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    @JsonProperty("CountryName")
    public String getCountryName() {
        return countryName;
    }

    @JsonProperty("CountryName")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Place withCountryName(String countryName) {
        this.countryName = countryName;
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

    public Place withIataCode(String iataCode) {
        this.iataCode = iataCode;
        return this;
    }

    @JsonProperty("SkyscannerCode")
    public String getSkyscannerCode() {
        return skyscannerCode;
    }

    @JsonProperty("SkyscannerCode")
    public void setSkyscannerCode(String skyscannerCode) {
        this.skyscannerCode = skyscannerCode;
    }

    public Place withSkyscannerCode(String skyscannerCode) {
        this.skyscannerCode = skyscannerCode;
        return this;
    }

    @JsonProperty("CityName")
    public String getCityName() {
        return cityName;
    }

    @JsonProperty("CityName")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Place withCityName(String cityName) {
        this.cityName = cityName;
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

    public Place withName(String name) {
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

    public Place withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(placeId).append(type).append(cityId).append(countryName).append(iataCode).append(skyscannerCode).append(cityName).append(name).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Place) == false) {
            return false;
        }
        Place rhs = ((Place) other);
        return new EqualsBuilder().append(placeId, rhs.placeId).append(type, rhs.type).append(cityId, rhs.cityId).append(countryName, rhs.countryName).append(iataCode, rhs.iataCode).append(skyscannerCode, rhs.skyscannerCode).append(cityName, rhs.cityName).append(name, rhs.name).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
