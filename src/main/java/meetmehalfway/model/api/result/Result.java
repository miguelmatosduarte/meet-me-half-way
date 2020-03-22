
package meetmehalfway.model.api.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "error",
        "searchResult"
})
public class Result {

    @JsonProperty("type")
    private String type;
    @JsonProperty("error")
    private Error error;
    @JsonProperty("searchResult")
    private List<SearchResult> searchResult;
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

    public Result withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("error")
    public Error getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Error error) {
        this.error = error;
    }

    public Result withError(Error error) {
        this.error = error;
        return this;
    }

    @JsonProperty("searchResult")
    public List<SearchResult> getSearchResult() {
        return searchResult;
    }

    @JsonProperty("searchResult")
    public void setSearchResult(List<SearchResult> searchResult) {
        this.searchResult = searchResult;
    }

    public Result withSearchResult(List<SearchResult> searchResult) {
        this.searchResult = searchResult;
        return this;
    }
}
