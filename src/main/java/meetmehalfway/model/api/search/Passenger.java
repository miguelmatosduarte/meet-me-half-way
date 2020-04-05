
package meetmehalfway.model.api.search;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "number",
        "origin",
        "departureDate",
        "returnDate"
})
public class Passenger {

    @JsonProperty("number")
    private int number;
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("departureDate")
    private String departureDate;
    @JsonProperty("returnDate")
    private String returnDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("number")
    public int getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(int number) {
        this.number = number;
    }

    @JsonProperty("origin")
    public String getOrigin() {
        return origin;
    }

    @JsonProperty("origin")
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Passenger withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    @JsonProperty("departureDate")
    public String getDepartureDate() {
        return departureDate;
    }

    @JsonProperty("departureDate")
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Passenger withDepartureDate(String departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    @JsonProperty("returnDate")
    public String getReturnDate() {
        return returnDate;
    }

    @JsonProperty("returnDate")
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Passenger withReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public boolean hasReturnDate(){
        return getReturnDate() != null && !getReturnDate().trim().isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Origin: %s, Departure Date: %s", getOrigin(), getDepartureDate());
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Passenger withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(origin).append(departureDate).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Passenger)) {
            return false;
        }
        Passenger rhs = ((Passenger) other);
        return new EqualsBuilder().append(origin, rhs.origin).append(departureDate, rhs.departureDate).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
