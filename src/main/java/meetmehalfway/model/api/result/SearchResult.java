
package meetmehalfway.model.api.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "city",
    "totalPrice",
    "currency",
    "passengerResults"
})
public class SearchResult implements Comparable< SearchResult > {

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

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public SearchResult withCity(String city) {
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

    public SearchResult withTotalPrice(Double totalPrice) {
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

    public SearchResult withCurrency(String currency) {
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

    public SearchResult withPassengerResults(List<PassengerResult> passengerResults) {
        this.passengerResults = passengerResults;
        return this;
    }

    @Override
    public int compareTo(SearchResult o) {
        return this.getTotalPrice().compareTo(o.getTotalPrice());
    }
}
