
package meetmehalfway.model.skyscanner.browseQuotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonDeserialize(builder = BrowseQuotesResponse.BrowseQuotesResponseBuilder.class)
@Builder(builderClassName = "BrowseQuotesResponseBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Carriers",
    "Quotes",
    "Currencies",
    "Places"
})
public class BrowseQuotesResponse {

    @JsonProperty("Carriers")
    private List<Carrier> carriers;
    @JsonProperty("Quotes")
    private List<Quote> quotes;
    @JsonProperty("Currencies")
    private List<Currency> currencies;
    @JsonProperty("Places")
    private List<Place> places;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BrowseQuotesResponseBuilder {
    }
}
