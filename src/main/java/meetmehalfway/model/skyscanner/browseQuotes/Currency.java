
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
@JsonDeserialize(builder = Currency.CurrencyBuilder.class)
@Builder(builderClassName = "CurrencyBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "DecimalSeparator",
    "ThousandsSeparator",
    "SymbolOnLeft",
    "SpaceBetweenAmountAndSymbol",
    "Symbol",
    "DecimalDigits",
    "Code",
    "RoundingCoefficient"
})
public class Currency {

    @JsonProperty("DecimalSeparator")
    private String decimalSeparator;
    @JsonProperty("ThousandsSeparator")
    private String thousandsSeparator;
    @JsonProperty("SymbolOnLeft")
    private Boolean symbolOnLeft;
    @JsonProperty("SpaceBetweenAmountAndSymbol")
    private Boolean spaceBetweenAmountAndSymbol;
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("DecimalDigits")
    private Integer decimalDigits;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("RoundingCoefficient")
    private Integer roundingCoefficient;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CurrencyBuilder {
    }
}
