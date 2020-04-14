
package meetmehalfway.model.skyscanner.browseQuotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@JsonDeserialize(builder = Quote.QuoteBuilder.class)
@Builder(builderClassName = "QuoteBuilder", toBuilder = true)
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
    private Map<String, Object> additionalProperties;
    private int passengerNumber;
    private boolean hasReturnDate;

    @JsonPOJOBuilder(withPrefix = "")
    public static class QuoteBuilder {
    }
}
