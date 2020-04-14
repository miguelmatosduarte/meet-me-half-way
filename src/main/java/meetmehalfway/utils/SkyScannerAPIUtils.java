package meetmehalfway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import meetmehalfway.model.api.search.Passenger;
import meetmehalfway.model.skyscanner.browseQuotes.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.geo.Geo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SkyScannerAPIUtils {

    private Logger logger = LoggerFactory.getLogger(SkyScannerAPIUtils.class);
    private static final int HTTP_OK_STATUS_CODE = 200;

    @Value("${partners.skyscanner.api}")
    private String partnersSkyscannerApi;

    @Value("${skyscanner.api.apiservices.endpoint}")
    private String skyscannerApiApiservicesEndpoint;

    @Value("${skyscanner.api.browsequotes.endpoint}")
    private String skyscannerApiBrowsequotesEndpoint;

    @Value("${skyscanner.api.version.endpoint}")
    private String skyscannerApiVersionEndpoint;

    @Value("${skyscanner.api.geo.endpoint}")
    private String skyscannerApiGeoEndpoint;

    @Value("${skyscanner.api.key}")
    private String skyscannerApiKey;

    @Value("${skyscanner.api.browse.quotes.country}")
    private String skyscannerApiBrowseQuotesCountry;

    @Value("${skyscanner.api.browse.quotes.currency}")
    private String skyscannerApiBrowseQuotesCurrency;

    @Value("${skyscanner.api.browse.quotes.locale}")
    private String skyscannerApiBrowseQuotesLocale;

    @Value("${skyscanner.api.browse.quotes.destination}")
    private String skyscannerApiBrowseQuotesDestination;


    public BrowseQuotesResponse browseQuotes(Passenger passenger) {
        BrowseQuotesResponse quotes = BrowseQuotesResponse.builder().build();

        StringBuilder url = new StringBuilder(
                String.format("%s%s%s%s/%s/%s/%s/%s/%s",
                        getSkyscannerApiEndpoint(),
                        skyscannerApiBrowsequotesEndpoint,
                        skyscannerApiVersionEndpoint,
                        skyscannerApiBrowseQuotesCountry,
                        skyscannerApiBrowseQuotesCurrency,
                        skyscannerApiBrowseQuotesLocale,
                        passenger.getOrigin(),
                        skyscannerApiBrowseQuotesDestination,
                        passenger.getDepartureDate())
        );

        if (passenger.hasReturnDate()){
            url.append(
                    String.format(
                            "/%s",
                            passenger.getReturnDate()
                    )
            );
        }

        url.append(
                String.format(
                        "?apikey=%s",
                        skyscannerApiKey
                )
        );

        try {

            int statusCode =-1;

            HttpResponse<JsonNode> response = null;

            while (statusCode != HTTP_OK_STATUS_CODE){
                response = Unirest.get(url.toString())
                        .asJson();

                statusCode = response.getStatus();
            }


            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            quotes = mapper.readValue(response.getBody().toString(), BrowseQuotesResponse.class);

        } catch (UnirestException | JsonProcessingException e) {
            logger.error("Error browsing quotes from SkyScanner. Exception: ", e);
        }
        return quotes;
    }

    public Geo geo() {
        Geo geo = Geo.builder().build();
        try {
            HttpResponse<JsonNode> response = Unirest.get(
                    String.format(
                            "%s%s%s?apikey=%s",
                            getSkyscannerApiEndpoint(),
                            skyscannerApiGeoEndpoint,
                            skyscannerApiVersionEndpoint,
                            skyscannerApiKey
                    )
            )
                    .asJson();

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            geo = mapper.readValue(response.getBody().toString(), Geo.class);
        } catch (UnirestException | JsonProcessingException e) {
            logger.error("Error getting places from SkyScanner. Exception: ", e);
        }
        return geo;
    }

    public String getSkyscannerApiEndpoint(){
        return String.format("https://%s%s", partnersSkyscannerApi, skyscannerApiApiservicesEndpoint);
    }
}
