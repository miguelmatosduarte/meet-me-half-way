package meetmehalfway.utils;

import meetmehalfway.model.City;
import meetmehalfway.model.api.result.PassengerResult;
import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.search.Passenger;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.model.skyscanner.browseQuotes.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.browseQuotes.Place;
import meetmehalfway.model.skyscanner.browseQuotes.Quote;
import meetmehalfway.model.QuoteCity;
import meetmehalfway.model.skyscanner.geo.Geo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuoteComparer {

    private List<BrowseQuotesResponse> quoteResponses;
    private Set<Place> places;
    private Geo geo;

    private City cheapestCity;

    private SkyScannerAPIUtils skyScannerAPIUtils;

    private static final String COUNTRY = "PT";
    private static final String EURO_CURRENCY = "EUR";
    private static final String LOCALE = "pt-PT";
    private static final String ANYWHERE_DESTINATION = "anywhere";
    private static final String RESULTS_TYPE = "result";

    public QuoteComparer(SkyScannerAPIUtils skyScannerAPIUtils){
        this.skyScannerAPIUtils = skyScannerAPIUtils;
    }

    public void loadQuotes(Passengers passengers) {
        this.geo = skyScannerAPIUtils.getPlacesGeo();
        this.quoteResponses = passengers.getPassengers().stream()
                .map(this::browseQuotes)
                .collect(Collectors.toList())
        ;
        fillPlaces();
    }

    private BrowseQuotesResponse browseQuotes(Passenger passenger) {
        String cityId = cityFromName(passenger.getOrigin()).getCityID();
        BrowseQuotesResponse browseQuotesResponse = skyScannerAPIUtils.browseQuotes(COUNTRY, EURO_CURRENCY, LOCALE, cityId, ANYWHERE_DESTINATION, passenger.getDepartureDate());
        browseQuotesResponse.getQuotes().forEach(
                q -> q.setPassengerNumber(passenger.getNumber())
        );
        return browseQuotesResponse;
    }

    private void fillPlaces(){
        List<Place> places = new ArrayList<>();
        quoteResponses.forEach(qr -> places.addAll(qr.getPlaces()));
        this.places = new HashSet<>(places);
    }

    private City cityFromName(String cityName) {
        final City[] city = new City[1];
        geo.getContinents()
                .forEach(
                        c -> c.getCountries()
                                .forEach(
                                        cou -> cou.getCities()
                                                .forEach(
                                                        cit -> {
                                                            if (cit.getName().equals(cityName)) {
                                                                final City myCity = new City();
                                                                myCity.setCityID(cit.getId());
                                                                myCity.setCityName(cit.getName());
                                                                city[0] = myCity;
                                                            }
                                                        }
                                                )
                                )
                );
        return city[0];
    }

    private City cityFromId(String cityId) {
        final City[] city = new City[1];
        geo.getContinents()
                .forEach(
                        c -> c.getCountries()
                                .forEach(
                                        cou -> cou.getCities()
                                                .forEach(
                                                        cit -> {
                                                            if (cit.getId().equals(cityId)) {
                                                                final City myCity = new City();
                                                                myCity.setCityID(cit.getId());
                                                                myCity.setCityName(cit.getName());
                                                                city[0] = myCity;
                                                            }
                                                        }
                                                )
                                )
                );
        return city[0];
    }

    public List<Quote> compareQuotes() {
        List<List<QuoteCity>> quotesByCity = new ArrayList<>();

        quoteResponses.forEach(qr ->
                quotesByCity.add(
                        minPricesPerCity(qr.getQuotes())
                ));

        Set<String> comparableCities = findComparableCities(quotesByCity);

        quotesByCity.forEach(
                list -> list.removeIf(s -> !comparableCities.contains(s.getCity().getCityID()))
        );

        setCheapestCity(findCheapestCityOverall(quotesByCity));

        List<Quote> finalQuotes = new ArrayList<>();

        quotesByCity.forEach(l ->
                finalQuotes.add(
                        l.stream().
                                filter(q -> q.getCity().getCityID().equals(cheapestCity.getCityID())).
                                findFirst()
                                .orElse(new QuoteCity(new City(), new Quote())).getQuote()
                )
        );

        return finalQuotes;
    }

    private List<QuoteCity> minPricesPerCity(List<Quote> quotes) {
        Map<String, List<Quote>> quotesByCityId =
                quotes.stream()
                        .collect(Collectors.groupingBy(p ->
                                getPlaceFromPlaceId(p.getOutboundLeg().getDestinationId()).getCityId()
                        ));

        return quotesByCityId.entrySet().stream()
                .map(x -> new QuoteCity(cityFromId(x.getKey()), Collections.min(x.getValue(), Comparator.comparing(Quote::getMinPrice))))
                .collect(Collectors.toList());
    }

    private Place getPlaceFromPlaceId(int placeId) {
        return places.stream()
                .filter(p -> p.getPlaceId() == placeId)
                .findAny().orElse(new Place());
    }

    private Set<String> findComparableCities(List<List<QuoteCity>> quotesByDestination) {

        List<Set<String>> citiesFromQuotes = getCitiesFromQuotes(quotesByDestination);

        boolean first = true;
        Set<String> comparableCities = new HashSet<>();

        for (Set<String> cities : citiesFromQuotes) {
            if (first) {
                comparableCities.addAll(cities);
                first = false;
            } else {
                comparableCities.retainAll(cities);
            }
        }

        return comparableCities;
    }

    public City getCheapestCity() {
        return cheapestCity;
    }

    public void setCheapestCity(City cheapestCity) {
        this.cheapestCity = cheapestCity;
    }

    public Result quotesToResult(List<Quote> quotes) {
        List<PassengerResult> passengerResults = new ArrayList<>();
        quotes.forEach(
                q -> passengerResults.add(new PassengerResult()
                        .withPrice(q.getMinPrice())
                        .withDepartureDate(q.getOutboundLeg().getDepartureDate())
                        .withNumber(q.getPassengerNumber())
                        .withOrigin(getPlaceFromPlaceId(q.getOutboundLeg().getOriginId()).getName())
                        .withDestination(getPlaceFromPlaceId(q.getOutboundLeg().getDestinationId()).getName())
                ));

        return new Result()
                .withCity(this.cheapestCity.getCityName())
                .withCurrency(EURO_CURRENCY)
                .withTotalPrice(
                        quotes.stream()
                                .map(Quote::getMinPrice)
                                .reduce(0.0, Double::sum)
                )
                .withPassengerResults(passengerResults)
                .withType(RESULTS_TYPE)
                ;
    }

    private List<Set<String>> getCitiesFromQuotes(List<List<QuoteCity>> quotesByDestination) {
        List<Set<String>> citiesFromQuotes = new ArrayList<>();
        for (List<QuoteCity> l : quotesByDestination) {
            citiesFromQuotes.add(
                    l.stream()
                            .map(q -> q.getCity().getCityID())
                            .collect(Collectors.toSet())
            );
        }
        return citiesFromQuotes;
    }

    private City findCheapestCityOverall(List<List<QuoteCity>> quotesByCity) {

        List<QuoteCity> joinedQuotesByDestination = joinListsOfQuotesByCity(quotesByCity);

        Map<String, Double> sumPricesPerCity = joinedQuotesByDestination.stream()
                .collect(Collectors.groupingBy(q -> q.getCity().getCityID(),
                        Collectors.summingDouble(q -> q.getQuote().getMinPrice())));

        return cityFromId(getMinKey(sumPricesPerCity));
    }

    private List<QuoteCity> joinListsOfQuotesByCity(List<List<QuoteCity>> quotesByCity) {
        Stream<QuoteCity> stream = Stream.of();
        for (List<QuoteCity> l : quotesByCity)
            stream = Stream.concat(stream, l.stream());

        return stream.collect(Collectors.toList());
    }

    private String getMinKey(Map<String, Double> map) {
        String minKey = null;
        double minValue = Double.MAX_VALUE;
        for (String key : map.keySet()) {
            double value = map.get(key);
            if (value < minValue) {
                minValue = value;
                minKey = key;
            }
        }
        return minKey;
    }
}
