
package json.schema.browsequotesresponse;

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
    "Carriers",
    "Quotes",
    "Currencies",
    "Places"
})
public class BrowseQuotesResponse {

    @JsonProperty("Carriers")
    private List<Carrier> carriers = new ArrayList<Carrier>();
    @JsonProperty("Quotes")
    private List<Quote> quotes = new ArrayList<Quote>();
    @JsonProperty("Currencies")
    private List<Currency> currencies = new ArrayList<Currency>();
    @JsonProperty("Places")
    private List<Place> places = new ArrayList<Place>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Carriers")
    public List<Carrier> getCarriers() {
        return carriers;
    }

    @JsonProperty("Carriers")
    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }

    public BrowseQuotesResponse withCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
        return this;
    }

    @JsonProperty("Quotes")
    public List<Quote> getQuotes() {
        return quotes;
    }

    @JsonProperty("Quotes")
    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public BrowseQuotesResponse withQuotes(List<Quote> quotes) {
        this.quotes = quotes;
        return this;
    }

    @JsonProperty("Currencies")
    public List<Currency> getCurrencies() {
        return currencies;
    }

    @JsonProperty("Currencies")
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public BrowseQuotesResponse withCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
        return this;
    }

    @JsonProperty("Places")
    public List<Place> getPlaces() {
        return places;
    }

    @JsonProperty("Places")
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public BrowseQuotesResponse withPlaces(List<Place> places) {
        this.places = places;
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

    public BrowseQuotesResponse withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(carriers).append(quotes).append(currencies).append(places).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BrowseQuotesResponse) == false) {
            return false;
        }
        BrowseQuotesResponse rhs = ((BrowseQuotesResponse) other);
        return new EqualsBuilder().append(carriers, rhs.carriers).append(quotes, rhs.quotes).append(currencies, rhs.currencies).append(places, rhs.places).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
