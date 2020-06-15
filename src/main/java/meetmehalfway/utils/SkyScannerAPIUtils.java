package meetmehalfway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import meetmehalfway.model.api.search.CitySelect;
import meetmehalfway.model.api.search.Passenger;
import meetmehalfway.model.skyscanner.SkyScannerApiResponse;
import meetmehalfway.model.skyscanner.browseQuotes.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.geo.Geo;
import meetmehalfway.model.skyscanner.validationErrors.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Service
public class SkyScannerAPIUtils {

    private Logger logger = LoggerFactory.getLogger(SkyScannerAPIUtils.class);
    private static final int HTTP_OK_STATUS_CODE = 200;
    private static final int HTTP_BAD_REQUEST_STATUS_CODE = 400;
    private static final int HTTP_REQUEST_LIMIT_EXCEEDED_STATUS_CODE = 429;

    private static final int NUM_REQUESTS_LIMIT = 10;

    private List<CitySelect> availableCities;

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

    public List<CitySelect> getAvailableCities(){
        return availableCities;
    }

    @PostConstruct
    private void loadCities() {

        logger.info("Loading available cities.");

        availableCities = new ArrayList<>();

        geo().getContinents().forEach(
                continent -> continent.getCountries().forEach(
                        country -> country.getCities().forEach(
                                city -> availableCities.add(
                                        new CitySelect()
                                                .withCityName(city.getName())
                                                .withCoutryName(city.getCountryId())
                                                .withid(city.getId())
                                )
                        )
                )
        );
    }


    public SkyScannerApiResponse browseQuotes(Passenger passenger) {
        BrowseQuotesResponse quotes;
        ValidationErrors validationErrors = ValidationErrors.builder().build();
        SkyScannerApiResponse skyScannerApiResponse = SkyScannerApiResponse.builder().build();

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

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            int numRequests = 0;
            loop: while (statusCode != HTTP_OK_STATUS_CODE && numRequests < NUM_REQUESTS_LIMIT){
                response = Unirest.get(url.toString())
                        .asJson();

                statusCode = response.getStatus();

                switch(statusCode){
                    case HTTP_BAD_REQUEST_STATUS_CODE:
                        validationErrors = mapper.readValue(response.getBody().toString(), ValidationErrors.class);
                        break loop;
                    case HTTP_REQUEST_LIMIT_EXCEEDED_STATUS_CODE:
                        numRequests++;
                        break;
                    default:
                        break;
                }
            }
            quotes = mapper.readValue(response.getBody().toString(), BrowseQuotesResponse.class);
            skyScannerApiResponse = SkyScannerApiResponse.builder()
                    .browseQuotesResponse(quotes)
                    .validationErrors(validationErrors)
                    .build()
                    ;

        } catch (UnirestException | JsonProcessingException e) {
            logger.error("Error browsing quotes from SkyScanner. Exception: ", e);
        }
        return skyScannerApiResponse;
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
