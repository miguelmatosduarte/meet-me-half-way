
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
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("DecimalSeparator")
    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    @JsonProperty("DecimalSeparator")
    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public Currency withDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
        return this;
    }

    @JsonProperty("ThousandsSeparator")
    public String getThousandsSeparator() {
        return thousandsSeparator;
    }

    @JsonProperty("ThousandsSeparator")
    public void setThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    public Currency withThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
        return this;
    }

    @JsonProperty("SymbolOnLeft")
    public Boolean getSymbolOnLeft() {
        return symbolOnLeft;
    }

    @JsonProperty("SymbolOnLeft")
    public void setSymbolOnLeft(Boolean symbolOnLeft) {
        this.symbolOnLeft = symbolOnLeft;
    }

    public Currency withSymbolOnLeft(Boolean symbolOnLeft) {
        this.symbolOnLeft = symbolOnLeft;
        return this;
    }

    @JsonProperty("SpaceBetweenAmountAndSymbol")
    public Boolean getSpaceBetweenAmountAndSymbol() {
        return spaceBetweenAmountAndSymbol;
    }

    @JsonProperty("SpaceBetweenAmountAndSymbol")
    public void setSpaceBetweenAmountAndSymbol(Boolean spaceBetweenAmountAndSymbol) {
        this.spaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
    }

    public Currency withSpaceBetweenAmountAndSymbol(Boolean spaceBetweenAmountAndSymbol) {
        this.spaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
        return this;
    }

    @JsonProperty("Symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("Symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Currency withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    @JsonProperty("DecimalDigits")
    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    @JsonProperty("DecimalDigits")
    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public Currency withDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
        return this;
    }

    @JsonProperty("Code")
    public String getCode() {
        return code;
    }

    @JsonProperty("Code")
    public void setCode(String code) {
        this.code = code;
    }

    public Currency withCode(String code) {
        this.code = code;
        return this;
    }

    @JsonProperty("RoundingCoefficient")
    public Integer getRoundingCoefficient() {
        return roundingCoefficient;
    }

    @JsonProperty("RoundingCoefficient")
    public void setRoundingCoefficient(Integer roundingCoefficient) {
        this.roundingCoefficient = roundingCoefficient;
    }

    public Currency withRoundingCoefficient(Integer roundingCoefficient) {
        this.roundingCoefficient = roundingCoefficient;
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

    public Currency withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(decimalSeparator).append(thousandsSeparator).append(symbolOnLeft).append(spaceBetweenAmountAndSymbol).append(symbol).append(decimalDigits).append(code).append(roundingCoefficient).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Currency)) {
            return false;
        }
        Currency rhs = ((Currency) other);
        return new EqualsBuilder().append(decimalSeparator, rhs.decimalSeparator).append(thousandsSeparator, rhs.thousandsSeparator).append(symbolOnLeft, rhs.symbolOnLeft).append(spaceBetweenAmountAndSymbol, rhs.spaceBetweenAmountAndSymbol).append(symbol, rhs.symbol).append(decimalDigits, rhs.decimalDigits).append(code, rhs.code).append(roundingCoefficient, rhs.roundingCoefficient).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
