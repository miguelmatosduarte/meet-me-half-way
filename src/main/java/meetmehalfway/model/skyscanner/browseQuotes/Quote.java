
package meetmehalfway.model.skyscanner.browseQuotes;

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
        "QuoteId",
        "QuoteDateTime",
        "MinPrice",
        "OutboundLeg",
        "InboundLeg",
        "Direct"
})
public class Quote {

    @JsonProperty("QuoteId")
    private Integer quoteId;
    @JsonProperty("QuoteDateTime")
    private String quoteDateTime;
    @JsonProperty("MinPrice")
    private Double minPrice;
    @JsonProperty("OutboundLeg")
    private FlightLeg outboundLeg;
    @JsonProperty("InboundLeg")
    private FlightLeg inboundLeg;
    @JsonProperty("Direct")
    private Boolean direct;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
    private int passengerNumber;
    private boolean hasReturnDate;

    @JsonProperty("QuoteId")
    public Integer getQuoteId() {
        return quoteId;
    }

    @JsonProperty("QuoteId")
    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
    }

    public Quote withQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    @JsonProperty("QuoteDateTime")
    public String getQuoteDateTime() {
        return quoteDateTime;
    }

    @JsonProperty("QuoteDateTime")
    public void setQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
    }

    public Quote withQuoteDateTime(String quoteDateTime) {
        this.quoteDateTime = quoteDateTime;
        return this;
    }

    @JsonProperty("MinPrice")
    public Double getMinPrice() {
        return minPrice;
    }

    @JsonProperty("MinPrice")
    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Quote withMinPrice(Double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    @JsonProperty("OutboundLeg")
    public FlightLeg getOutboundLeg() {
        return outboundLeg;
    }

    @JsonProperty("OutboundLeg")
    public void setOutboundLeg(FlightLeg outboundLeg) {
        this.outboundLeg = outboundLeg;
    }

    public Quote withOutboundLeg(FlightLeg outboundLeg) {
        this.outboundLeg = outboundLeg;
        return this;
    }

    @JsonProperty("InboundLeg")
    public FlightLeg getInboundLeg() {
        return inboundLeg;
    }

    @JsonProperty("InboundLeg")
    public void setInboundLeg(FlightLeg inboundLeg) {
        this.inboundLeg = inboundLeg;
    }

    public Quote withInboundLeg(FlightLeg inboundLeg) {
        this.inboundLeg = inboundLeg;
        return this;
    }

    @JsonProperty("Direct")
    public Boolean getDirect() {
        return direct;
    }

    @JsonProperty("Direct")
    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    public Quote withDirect(Boolean direct) {
        this.direct = direct;
        return this;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
    }

    public boolean getHasReturnDate() {
        return hasReturnDate;
    }

    public void setHasReturnDate(boolean hasReturnDate) {
        this.hasReturnDate = hasReturnDate;
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

    public Quote withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(quoteId).append(quoteDateTime).append(minPrice).append(outboundLeg).append(direct).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Quote)) {
            return false;
        }
        Quote rhs = ((Quote) other);
        return new EqualsBuilder().append(quoteId, rhs.quoteId).append(quoteDateTime, rhs.quoteDateTime).append(minPrice, rhs.minPrice).append(outboundLeg, rhs.outboundLeg).append(direct, rhs.direct).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
