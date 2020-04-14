
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
@JsonDeserialize(builder = FlightLeg.FlightLegBuilder.class)
@Builder(builderClassName = "FlightLegBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CarrierIds",
    "DepartureDate",
    "OriginId",
    "DestinationId"
})
public class FlightLeg {

    @JsonProperty("CarrierIds")
    private List<Integer> carrierIds;
    @JsonProperty("DepartureDate")
    private String departureDate;
    @JsonProperty("OriginId")
    private Integer originId;
    @JsonProperty("DestinationId")
    private Integer destinationId;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FlightLegBuilder {
    }
}
