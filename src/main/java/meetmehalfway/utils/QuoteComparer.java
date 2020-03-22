package meetmehalfway.utils;

import meetmehalfway.model.QuoteCity;
import meetmehalfway.model.api.result.Error;
import meetmehalfway.model.api.result.PassengerResult;
import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.result.SearchResult;
import meetmehalfway.model.api.search.Passenger;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.model.skyscanner.browseQuotes.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.browseQuotes.Carrier;
import meetmehalfway.model.skyscanner.browseQuotes.Place;
import meetmehalfway.model.skyscanner.browseQuotes.Quote;
import meetmehalfway.model.skyscanner.geo.City;
import meetmehalfway.model.skyscanner.geo.Geo;

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

    private SkyScannerAPIUtils skyScannerAPIUtils;
    private Passengers passengers;
    private Geo geo;
    private Set<Place> places;
    private Set<Carrier> carriers;

    private void setPlaces(Set<Place> places) {
        this.places = places;
    }

    private Set<Place> getPlaces() {
        return this.places;
    }

    private void setCarriers(Set<Carrier> carriers) {
        this.carriers = carriers;
    }

    private Set<Carrier> getCarriers() {
        return this.carriers;
    }

    public QuoteComparer(Passengers passengers, SkyScannerAPIUtils skyScannerAPIUtils) {
        this.passengers = passengers;
        this.skyScannerAPIUtils = skyScannerAPIUtils;
        this.geo = skyScannerAPIUtils.geo();
    }


    public Result compareQuotes() {

        List<List<QuoteCity>> quotesByCity = new ArrayList<>();

        List<BrowseQuotesResponse> browseQuotesResponse = getQuotesFromPassengers();

        setPlaces(getSetOfPlaces(browseQuotesResponse));

        setCarriers(getSetOfCarriers(browseQuotesResponse));

        browseQuotesResponse.forEach(qr ->
                quotesByCity.add(
                        minPricesPerCity(qr.getQuotes())
                ));

        Set<String> comparableCities = findComparableCities(quotesByCity);

        if (comparableCities.size() == 0) {
            return new Result()
                    .withType("error")
                    .withError(
                            new Error()
                            .withCode(ErrorType.NO_COMMON_DESTINATION.getCode())
                            .withMessage(ErrorType.NO_COMMON_DESTINATION.getDescription())
                    )
                    ;
        }

        quotesByCity.forEach(
                list -> list.removeIf(s -> !comparableCities.contains(s.getCity().getId()))
        );

        City cheapestCity = findCheapestCityOverall(quotesByCity);

        List<Quote> finalQuotes = new ArrayList<>();

        quotesByCity.forEach(l ->
                finalQuotes.add(
                        l.stream().
                                filter(
                                        q -> q.getCity().getId().equals(cheapestCity.getId())
                                ).findFirst()
                                .orElse(new QuoteCity(new City(), new Quote()))
                                .getQuote()
                )
        );

        return new Result()
                .withType("result")
                .withSearchResult(
                        quotesToSearchResult(finalQuotes, cheapestCity)
                );
    }


    private List<BrowseQuotesResponse> getQuotesFromPassengers() {
        return passengers.getPassengers().stream()
                .map(this::browseQuotes)
                .collect(Collectors.toList())
                ;
    }


    private BrowseQuotesResponse browseQuotes(Passenger passenger) {

        BrowseQuotesResponse browseQuotesResponse = skyScannerAPIUtils
                .browseQuotes(
                        passenger.getOrigin(),
                        passenger.getDepartureDate()
                );

        browseQuotesResponse.getQuotes().forEach(
                q -> q.setPassengerNumber(passenger.getNumber())
        );
        return browseQuotesResponse;
    }


    private List<QuoteCity> minPricesPerCity(List<Quote> quotes) {
        Map<String, List<Quote>> quotesByCityId =
                quotes.stream()
                        .collect(
                                Collectors.groupingBy(
                                        p -> getPlaceFromPlaceId(
                                                getPlaces(),
                                                p.getOutboundLeg().getDestinationId()
                                        ).getCityId()
                                )
                        );

        return quotesByCityId
                .entrySet()
                .stream()
                .map(
                        x -> new QuoteCity(
                                geo.fromCityId(x.getKey()),
                                Collections.min(
                                        x.getValue(),
                                        Comparator.comparing(Quote::getMinPrice)
                                )
                        )
                )
                .collect(Collectors.toList());
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


    private List<Set<String>> getCitiesFromQuotes(List<List<QuoteCity>> quotesByDestination) {
        List<Set<String>> citiesFromQuotes = new ArrayList<>();

        for (List<QuoteCity> l : quotesByDestination) {
            citiesFromQuotes.add(
                    l.stream()
                            .map(q -> q.getCity().getId())
                            .collect(Collectors.toSet())
            );
        }
        return citiesFromQuotes;
    }


    private City findCheapestCityOverall(List<List<QuoteCity>> quotesByCity) {

        List<QuoteCity> joinedQuotesByDestination = joinListsOfQuotesByCity(quotesByCity);

        Map<String, Double> sumPricesPerCity = joinedQuotesByDestination
                .stream()
                .collect(Collectors.groupingBy(
                        q -> q.getCity().getId(),
                        Collectors.summingDouble(
                                q -> q.getQuote().getMinPrice()
                        )
                        )
                );

        return geo.fromCityId(getMinKey(sumPricesPerCity));
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


    private SearchResult quotesToSearchResult(List<Quote> quotes, City cheapestCity) {
        List<PassengerResult> passengerResults = new ArrayList<>();
        quotes.forEach(
                q -> passengerResults.add(new PassengerResult()
                        .withPrice(q.getMinPrice())
                        .withDepartureDate(q.getOutboundLeg().getDepartureDate())
                        .withNumber(q.getPassengerNumber())
                        .withOrigin(getPlaceFromPlaceId(getPlaces(), q.getOutboundLeg().getOriginId()).getName())
                        .withDestination(getPlaceFromPlaceId(getPlaces(), q.getOutboundLeg().getDestinationId()).getName())
                        .withCarriers(getCarriersFromIds(q.getOutboundLeg().getCarrierIds()))
                ));

        return new SearchResult()
                .withCity(cheapestCity.getName())
                .withCurrency("EUR")
                .withTotalPrice(
                        quotes.stream()
                                .map(Quote::getMinPrice)
                                .reduce(0.0, Double::sum)
                )
                .withPassengerResults(passengerResults)
                ;
    }


    private Set<Place> getSetOfPlaces(List<BrowseQuotesResponse> quoteResponses){
        List<Place> places = new ArrayList<>();
        quoteResponses.forEach(qr -> places.addAll(qr.getPlaces()));
        return new HashSet<>(places);
    }

    private Set<Carrier> getSetOfCarriers(List<BrowseQuotesResponse> quoteResponses){
        List<Carrier> carriers = new ArrayList<>();
        quoteResponses.forEach(qr -> carriers.addAll(qr.getCarriers()));
        return new HashSet<>(carriers);
    }


    private Place getPlaceFromPlaceId(Set<Place> places, int placeId) {
        return places.stream()
                .filter(p -> p.getPlaceId() == placeId)
                .findAny().orElse(new Place());
    }

    private Carrier getCarrierFromId(Set<Carrier> carriers, int carrierId) {
        return carriers.stream()
                .filter(p -> p.getCarrierId() == carrierId)
                .findAny().orElse(new Carrier());
    }

    private List<String> getCarriersFromIds(List<Integer> carrierIds) {
        return carrierIds.stream()
                .map(id -> getCarrierFromId(getCarriers(), id).getName())
                .collect(Collectors.toList());
    }






}
