
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
    "CarrierIds",
    "DepartureDate",
    "OriginId",
    "DestinationId"
})
public class OutboundLeg {

    @JsonProperty("CarrierIds")
    private List<Integer> carrierIds = new ArrayList<Integer>();
    @JsonProperty("DepartureDate")
    private String departureDate;
    @JsonProperty("OriginId")
    private Integer originId;
    @JsonProperty("DestinationId")
    private Integer destinationId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("CarrierIds")
    public List<Integer> getCarrierIds() {
        return carrierIds;
    }

    @JsonProperty("CarrierIds")
    public void setCarrierIds(List<Integer> carrierIds) {
        this.carrierIds = carrierIds;
    }

    public OutboundLeg withCarrierIds(List<Integer> carrierIds) {
        this.carrierIds = carrierIds;
        return this;
    }

    @JsonProperty("DepartureDate")
    public String getDepartureDate() {
        return departureDate;
    }

    @JsonProperty("DepartureDate")
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public OutboundLeg withDepartureDate(String departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    @JsonProperty("OriginId")
    public Integer getOriginId() {
        return originId;
    }

    @JsonProperty("OriginId")
    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public OutboundLeg withOriginId(Integer originId) {
        this.originId = originId;
        return this;
    }

    @JsonProperty("DestinationId")
    public Integer getDestinationId() {
        return destinationId;
    }

    @JsonProperty("DestinationId")
    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public OutboundLeg withDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
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

    public OutboundLeg withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(carrierIds).append(departureDate).append(originId).append(destinationId).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OutboundLeg) == false) {
            return false;
        }
        OutboundLeg rhs = ((OutboundLeg) other);
        return new EqualsBuilder().append(carrierIds, rhs.carrierIds).append(departureDate, rhs.departureDate).append(originId, rhs.originId).append(destinationId, rhs.destinationId).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
