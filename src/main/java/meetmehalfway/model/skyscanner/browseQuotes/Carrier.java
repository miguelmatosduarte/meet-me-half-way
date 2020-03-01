
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
    "CarrierId",
    "Name"
})
public class Carrier {

    @JsonProperty("CarrierId")
    private Integer carrierId;
    @JsonProperty("Name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("CarrierId")
    public Integer getCarrierId() {
        return carrierId;
    }

    @JsonProperty("CarrierId")
    public void setCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
    }

    public Carrier withCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
        return this;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public Carrier withName(String name) {
        this.name = name;
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

    public Carrier withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(carrierId).append(name).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Carrier)) {
            return false;
        }
        Carrier rhs = ((Carrier) other);
        return new EqualsBuilder().append(carrierId, rhs.carrierId).append(name, rhs.name).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
