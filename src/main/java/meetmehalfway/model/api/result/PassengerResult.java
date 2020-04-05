
package meetmehalfway.model.api.result;

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
        "number",
        "origin",
        "destination",
        "departureDate",
        "returnData",
        "price",
        "Carrier"
})
public class PassengerResult {

    @JsonProperty("number")
    private Integer number;
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("departureDate")
    private String departureDate;
    @JsonProperty("returnDate")
    private String returnDate;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("carriers")
    private List<String> carriers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    public PassengerResult withNumber(Integer number) {
        this.number = number;
        return this;
    }

    @JsonProperty("origin")
    public String getOrigin() {
        return origin;
    }

    @JsonProperty("origin")
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public PassengerResult withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    @JsonProperty("destination")
    public String getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public PassengerResult withDestination(String destination) {
        this.destination = destination;
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

    public PassengerResult withDepartureDate(String departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    @JsonProperty("returnDate")
    public String getReturneDate() {
        return returnDate;
    }

    @JsonProperty("departureDate")
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public PassengerResult withReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    public PassengerResult withPrice(Double price) {
        this.price = price;
        return this;
    }

    @JsonProperty("carriers")
    public List<String> getCarriers() {
        return carriers;
    }

    @JsonProperty("carriers")
    public void setCarriers(List<String> carriers) {
        this.carriers = carriers;
    }

    public PassengerResult withCarriers(List<String> carriers) {
        this.carriers = carriers;
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

    public PassengerResult withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(number).append(origin).append(departureDate).append(price).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PassengerResult)) {
            return false;
        }
        PassengerResult rhs = ((PassengerResult) other);
        return new EqualsBuilder().append(number, rhs.number).append(origin, rhs.origin).append(departureDate, rhs.departureDate).append(price, rhs.price).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
