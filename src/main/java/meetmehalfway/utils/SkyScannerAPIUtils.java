package meetmehalfway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import meetmehalfway.model.skyscanner.browseQuotes.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.geo.Geo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SkyScannerAPIUtils {

    private Logger logger = LoggerFactory.getLogger(SkyScannerAPIUtils.class);

    @Value("${skyscanner.api.host}")
    private String skyscannerApiHost;

    @Value("${skyscanner.api.browse.quotes.endpoint}")
    private String skyscannerApiBroswQuotesEndpoint;

    @Value("${skyscanner.api.key}")
    private String skyscannerApiKey;

    @Value("${partners.skyscanner.api}")
    private String partnersSkyscannerApi;

    @Value("${skyscanner.api.geo.endpoint}")
    private String skyscannerApiGeoEndpoint;

    @Value("${skyscanner.api.version}")
    private String skyscannerApiVersion;

    @Value("${skyscanner.api.geo.key}")
    private String skyscannerApiGeoKey;

    @Value("${skyscanner.api.host.header}")
    private String skyscannerApiHostHeader;

    @Value("${skyscanner.api.key.header}")
    private String skyscannerApiKeyHeader;

    @Value("${skyscanner.api.browse.quotes.country}")
    private String skyscannerApiBrowseQuotesCountry;

    @Value("${skyscanner.api.browse.quotes.currency}")
    private String skyscannerApiBrowseQuotesCurrency;

    @Value("${skyscanner.api.browse.quotes.locale}")
    private String skyscannerApiBrowseQuotesLocale;

    @Value("${skyscanner.api.browse.quotes.destination}")
    private String skyscannerApiBrowseQuotesDestination;


    public BrowseQuotesResponse browseQuotes(String originPlace, String outboundPartialDate) {
        BrowseQuotesResponse quotes = new BrowseQuotesResponse();
        try {
            HttpResponse<JsonNode> response = Unirest.get(
                    String.format("https://%s%s%s/%s/%s/%s/%s/%s",
                            skyscannerApiHost,
                            skyscannerApiBroswQuotesEndpoint,
                            skyscannerApiBrowseQuotesCountry,
                            skyscannerApiBrowseQuotesCurrency,
                            skyscannerApiBrowseQuotesLocale,
                            originPlace,
                            skyscannerApiBrowseQuotesDestination,
                            outboundPartialDate)
            )
                    .header(skyscannerApiHostHeader, skyscannerApiHost)
                    .header(skyscannerApiKeyHeader, skyscannerApiKey)
                    .asJson();

            ObjectMapper mapper = new ObjectMapper();
            quotes = mapper.readValue(response.getBody().toString(), BrowseQuotesResponse.class);
        } catch (UnirestException | JsonProcessingException e) {
            logger.error("Error browsing quotes from SkyScanner. Exception: ", e);
        }
        return quotes;
    }

    public Geo geo() {
        Geo geo = new Geo();
        try {
            HttpResponse<JsonNode> response = Unirest.get(String.format("https://%sapikey=%s", partnersSkyscannerApi + skyscannerApiGeoEndpoint + skyscannerApiVersion + "?", skyscannerApiGeoKey))
                    .asJson();

            ObjectMapper mapper = new ObjectMapper();
            geo = mapper.readValue(response.getBody().toString(), Geo.class);
        } catch (UnirestException | JsonProcessingException e) {
            logger.error("Error getting places from SkyScanner. Exception: ", e);
        }
        return geo;
    }
}
