package meetmehalfway.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import meetmehalfway.model.skyscanner.browseQuotes.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.geo.Geo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SkyScannerAPIUtils {

    private Logger logger = LoggerFactory.getLogger(SkyScannerAPIUtils.class);

    private static final int HTTP_OK_STATUS_CODE = 201;
    private static final String X_RAPIDAPI_HOST = "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com";
    private static final String X_RAPIDAPI_PRICING_ENDPOINT = "/apiservices/pricing/v1.0/";
    private static final String X_RAPIDAPI_BROWSE_QUOTES_ENDPOINT = "/apiservices/browsequotes/v1.0/";
    private static final String X_RAPIDAPI_PRICING_UK_ENDPOINT = "/apiservices/pricing/uk2/v1.0/";
    private static final String X_RAPIDAPI_PRICING_KEY = "2aaae45b8bmsh9918702b54421acp165237jsn763c1f50ee62";
    private static final String SKYSCANNER_API = "partners.api.skyscanner.net/apiservices/";
    private static final String PRICING = "pricing/uk2/";
    private static final String PLACES_GEO = "geo/";
    private static final String API_VERSION = "v1.0";
    private static final String GEO_API_KEY= "prtl6749387986743898559646983194";
    private String sessionKey;

    public SkyScannerAPIUtils() {
        this.sessionKey = this.postCreateSession();
        System.out.println("Session key: " + this.sessionKey);
    }

    private String postCreateSession() {

        String sessionKey = "";

        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 6);
            Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateAsString = dateFormat.format(date);

            HttpResponse<JsonNode> response = Unirest.post(String.format("https://%s%s", X_RAPIDAPI_HOST, X_RAPIDAPI_PRICING_ENDPOINT))
                    .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                    .header("X-RapidAPI-Key", X_RAPIDAPI_PRICING_KEY)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("inboundDate", dateAsString)
                    .field("cabinClass", "business")
                    .field("children", 0)
                    .field("infants", 0)
                    .field("country", "US")
                    .field("currency", "USD")
                    .field("locale", "en-US")
                    .field("originPlace", "SFO-sky")
                    .field("destinationPlace", "LHR-sky")
                    .field("outboundDate", dateAsString)
                    .field("adults", 1)
                    .asJson();

            if (response.getStatus() == HTTP_OK_STATUS_CODE) {
                String pattern = "^http://" + SKYSCANNER_API + PRICING + API_VERSION + "/" + "(.+)$";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(response.getHeaders().get("Location").get(0));

                if (m.find()) {
                    sessionKey = m.group(1);
                    logger.info("Session Key: " + sessionKey);
                } else {
                    logger.error("No session key was obtained!");
                }
            }

        } catch (UnirestException e) {
            logger.error("Error creating session! Exception: ", e);
            return sessionKey;
        }
        return sessionKey;
    }

    public void pollSessionResults() {
        try {
            HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s%s%s", X_RAPIDAPI_HOST, X_RAPIDAPI_PRICING_UK_ENDPOINT, this.sessionKey))
                    .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                    .header("X-RapidAPI-Key", X_RAPIDAPI_PRICING_KEY)
                    .asJson();

        } catch (UnirestException e) {
            logger.error("Error polling session results! Exception: ", e);
        }
    }

    public BrowseQuotesResponse browseQuotes(String country, String currency, String locale, String originPlace, String destinationPlace, String outboundPartialDate) {
        BrowseQuotesResponse quotes = new BrowseQuotesResponse();
        try {
            HttpResponse<JsonNode> response = Unirest.get(String.format("https://%s%s%s/%s/%s/%s/%s/%s", X_RAPIDAPI_HOST, X_RAPIDAPI_BROWSE_QUOTES_ENDPOINT, country, currency, locale, originPlace, destinationPlace, outboundPartialDate))
                    .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                    .header("X-RapidAPI-Key", X_RAPIDAPI_PRICING_KEY)
                    .asJson();

            ObjectMapper mapper = new ObjectMapper();
            quotes = mapper.readValue(response.getBody().toString(), BrowseQuotesResponse.class);
        } catch (Exception e) {
            logger.error("Error browsing quotes! Exception: ", e);
        }
        return quotes;
    }

    public Geo getPlacesGeo() {
        Geo geo = new Geo();
        try {
            HttpResponse<JsonNode> response = Unirest.get(String.format("https://%sapikey=%s", SKYSCANNER_API + PLACES_GEO + API_VERSION + "?", GEO_API_KEY))
                    .asJson();
            ObjectMapper mapper = new ObjectMapper();
            geo = mapper.readValue(response.getBody().toString(), Geo.class);
        } catch (Exception e) {
            logger.error("Error getting places with Geo API! Exception: ", e);
        }
        return geo;
    }
}
