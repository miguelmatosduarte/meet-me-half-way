
package meetmehalfway.model.api.result;

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
    "type",
    "city",
    "totalPrice",
    "currency",
    "passengerResults"
})
public class Result {

    @JsonProperty("type")
    private String type;
    @JsonProperty("city")
    private String city;
    @JsonProperty("totalPrice")
    private Double totalPrice;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("passengerResults")
    private List<PassengerResult> passengerResults = new ArrayList<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Result withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public Result withCity(String city) {
        this.city = city;
        return this;
    }

    @JsonProperty("totalPrice")
    public Double getTotalPrice() {
        return totalPrice;
    }

    @JsonProperty("totalPrice")
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Result withTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Result withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @JsonProperty("passengerResults")
    public List<PassengerResult> getPassengerResults() {
        return passengerResults;
    }

    @JsonProperty("passengerResults")
    public void setPassengerResults(List<PassengerResult> passengerResults) {
        this.passengerResults = passengerResults;
    }

    public Result withPassengerResults(List<PassengerResult> passengerResults) {
        this.passengerResults = passengerResults;
        return this;
    }
}
