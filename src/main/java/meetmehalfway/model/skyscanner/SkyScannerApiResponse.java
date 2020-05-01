package meetmehalfway.model.skyscanner;

import lombok.Builder;
import lombok.Data;
import meetmehalfway.model.skyscanner.browseQuotes.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.validationErrors.ValidationErrors;

@Data
@Builder
public class SkyScannerApiResponse {
    private BrowseQuotesResponse browseQuotesResponse;
    private ValidationErrors validationErrors;
    private int passengerNumber;
    private boolean hasReturnDate;
}
