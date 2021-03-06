package meetmehalfway.utils;

import meetmehalfway.model.QuoteCity;
import meetmehalfway.model.api.result.Error;
import meetmehalfway.model.api.result.PassengerResult;
import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.result.SearchResult;
import meetmehalfway.model.api.search.Passenger;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.model.skyscanner.SkyScannerApiResponse;
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

        List<SkyScannerApiResponse> skyScannerApiResponse = getQuotesFromPassengers();

        if (skyScannerApiResponse.stream()
                .anyMatch(r -> r.getValidationErrors().getValidationErrors() != null)) {
            List<Error> validationErrors = Error.parseValidationErrors(skyScannerApiResponse);
            return Result.builder()
                    .type("error")
                    .errors(validationErrors)
                    .build();
        }

        List<BrowseQuotesResponse> browseQuotesResponse = skyScannerApiResponse.stream()
                .map(SkyScannerApiResponse::getBrowseQuotesResponse)
                .collect(Collectors.toList());

        setPlaces(getSetOfPlaces(browseQuotesResponse));
        setCarriers(getSetOfCarriers(browseQuotesResponse));

        browseQuotesResponse.forEach(qr ->
                quotesByCity.add(
                        minPricesPerCity(qr.getQuotes())
                ));

        Set<String> comparableCities = findComparableCities(quotesByCity);

        if (comparableCities.size() == 0) {
            return Result.builder()
                    .type("error")
                    .errors(
                            Collections.singletonList(
                                    Error.builder()
                                            .code(ErrorType.NO_COMMON_DESTINATION.getCode())
                                            .message(ErrorType.NO_COMMON_DESTINATION.getDescription())
                                            .build()
                            )
                    ).build()
                    ;
        }

        quotesByCity.forEach(
                list -> list.removeIf(s -> !comparableCities.contains(s.getCity().getId()))
        );

        Map<City, Double> citiesOrderedByOverallPrice = getCitiesOrderedByOverallPrice(quotesByCity);

        List<SearchResult> searchResults = getSearchResultsFromQuotes(citiesOrderedByOverallPrice, quotesByCity);

        Collections.sort(searchResults);

        return Result.builder()
                .type("result")
                .searchResult(searchResults)
                .build();
    }

    private List<SearchResult> getSearchResultsFromQuotes(Map<City, Double> citiesOrderedByOverallPrice, List<List<QuoteCity>> quotesByCity) {

        List<SearchResult> searchResults = new ArrayList<>();

        for (Map.Entry<City, Double> entry : citiesOrderedByOverallPrice.entrySet()) {

            List<Quote> cityQuotes = new ArrayList<>();

            quotesByCity.forEach(l ->
                    cityQuotes.add(
                            l.stream().
                                    filter(
                                            q -> q.getCity().getId().equals(entry.getKey().getId())
                                    ).findFirst()
                                    .orElse(new QuoteCity(City.builder().build(), Quote.builder().build()))
                                    .getQuote()
                    )
            );

            searchResults.add(
                    quotesToSearchResult(cityQuotes, entry.getKey(), entry.getValue())
            );
        }

        return searchResults;
    }


    private List<SkyScannerApiResponse> getQuotesFromPassengers() {
        return passengers.getPassengers().stream()
                .map(this::browseQuotes)
                .collect(Collectors.toList())
                ;
    }


    private SkyScannerApiResponse browseQuotes(Passenger passenger) {

        SkyScannerApiResponse skyScannerApiResponse = skyScannerAPIUtils
                .browseQuotes(
                        passenger
                );

        skyScannerApiResponse.setPassengerNumber(
                passenger.getNumber()
        );
        skyScannerApiResponse.setHasReturnDate(
                passenger.hasReturnDate()
        );

        if (skyScannerApiResponse.getBrowseQuotesResponse().getQuotes() != null) {
            skyScannerApiResponse.getBrowseQuotesResponse().getQuotes().forEach(
                    q -> {
                        q.setPassengerNumber(passenger.getNumber());
                        q.setHasReturnDate(passenger.hasReturnDate());
                    }
            );
        }
        return skyScannerApiResponse;
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


    private Map<City, Double> getCitiesOrderedByOverallPrice(List<List<QuoteCity>> quotesByCity) {

        List<QuoteCity> joinedQuotesByDestination = joinListsOfQuotesByCity(quotesByCity);

        return joinedQuotesByDestination
                .stream()
                .collect(Collectors.groupingBy(
                        QuoteCity::getCity,
                        Collectors.summingDouble(
                                q -> q.getQuote().getMinPrice()
                        )
                        )
                );
    }


    private List<QuoteCity> joinListsOfQuotesByCity(List<List<QuoteCity>> quotesByCity) {
        Stream<QuoteCity> stream = Stream.of();

        for (List<QuoteCity> l : quotesByCity)
            stream = Stream.concat(stream, l.stream());

        return stream.collect(Collectors.toList());
    }

    private SearchResult quotesToSearchResult(List<Quote> quotes, City city, Double overallPrice) {
        List<PassengerResult> passengerResults = new ArrayList<>();
        quotes.forEach(
                q -> passengerResults.add(new PassengerResult()
                        .withPrice(q.getMinPrice())
                        .withDepartureDate(q.getOutboundLeg().getDepartureDate())
                        .withReturnDate(q.isHasReturnDate() ? q.getInboundLeg().getDepartureDate() : "")
                        .withNumber(q.getPassengerNumber())
                        .withOrigin(getPlaceFromPlaceId(getPlaces(), q.getOutboundLeg().getOriginId()).getName())
                        .withDestination(getPlaceFromPlaceId(getPlaces(), q.getOutboundLeg().getDestinationId()).getName())
                        .withCarriers(getCarriersFromIds(q.getOutboundLeg().getCarrierIds()))
                ));

        return new SearchResult()
                .withCity(city.getName())
                .withCurrency("EUR")
                .withTotalPrice(overallPrice)
                .withPassengerResults(passengerResults)
                ;
    }


    private Set<Place> getSetOfPlaces(List<BrowseQuotesResponse> quoteResponses) {
        List<Place> places = new ArrayList<>();
        quoteResponses.forEach(qr -> places.addAll(qr.getPlaces()));
        return new HashSet<>(places);
    }

    private Set<Carrier> getSetOfCarriers(List<BrowseQuotesResponse> quoteResponses) {
        List<Carrier> carriers = new ArrayList<>();
        quoteResponses.forEach(qr -> carriers.addAll(qr.getCarriers()));
        return new HashSet<>(carriers);
    }


    private Place getPlaceFromPlaceId(Set<Place> places, int placeId) {
        return places.stream()
                .filter(p -> p.getPlaceId() == placeId)
                .findAny().orElse(Place.builder().build());
    }

    private Carrier getCarrierFromId(Set<Carrier> carriers, int carrierId) {
        return carriers.stream()
                .filter(p -> p.getCarrierId() == carrierId)
                .findAny().orElse(Carrier.builder().build());
    }

    private List<String> getCarriersFromIds(List<Integer> carrierIds) {
        return carrierIds.stream()
                .map(id -> getCarrierFromId(getCarriers(), id).getName())
                .collect(Collectors.toList());
    }
}