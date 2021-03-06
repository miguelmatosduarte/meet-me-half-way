
package meetmehalfway.model.api.search;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "passengers"
})
public class Passengers {

    @JsonProperty("type")
    private String type;
    @JsonProperty("passengers")
    private List<Passenger> passengers = new ArrayList<>();
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

    public Passengers withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("passengers")
    public List<Passenger> getPassengers() {
        return passengers;
    }

    @JsonProperty("passengers")
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Passengers withPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
        return this;
    }

    @Override
    public String toString() {
        return String.join(" ; ", getPassengers().toString());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(passengers).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Passengers)) {
            return false;
        }
        Passengers rhs = ((Passengers) other);
        return new EqualsBuilder().append(type, rhs.type).append(passengers, rhs.passengers).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
