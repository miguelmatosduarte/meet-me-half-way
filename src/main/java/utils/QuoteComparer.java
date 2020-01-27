package utils;

import json.schema.browsequotesresponse.BrowseQuotesResponse;
import json.schema.browsequotesresponse.Place;
import json.schema.browsequotesresponse.Quote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuoteComparer {

    private List<BrowseQuotesResponse> quoteResponses;

    public QuoteComparer(List<BrowseQuotesResponse> quoteResponses){
        this.quoteResponses = quoteResponses;
    }

    public List<Quote> compareQuotes(){
        List<List<QuoteCity>> quotesByCity = new ArrayList<>();

        quoteResponses.forEach(
                qr -> quotesByCity.add(
                        minPricesPerCity(qr.getQuotes(), qr.getPlaces())
                )
        );

        Set<String> comparableCities = findComparableCities(quotesByCity);

        quotesByCity.forEach(
          list -> list.removeIf(s -> comparableCities.contains(s.getCityId()))
        );

        String cheapestCity = findCheapestCityOverall(quotesByCity);

        List<Quote> finalQuotes = new ArrayList<>();

        quotesByCity.forEach( l ->
                finalQuotes.add(
                        l.stream().
                                filter(q -> q.getCityId().equals(cheapestCity)).
                                findFirst()
                                .orElse(new QuoteCity("",new Quote())).getQuote()
                )
        );

        return finalQuotes;
    }

    private  List<QuoteCity> minPricesPerCity(List<Quote> quotes, List<Place> places){
        Map<String, List<Quote>> quotesByCityId =
                quotes.stream()
                        .collect(Collectors.groupingBy(p ->
                                findCity(p.getOutboundLeg().getDestinationId(), places)
                        ));

        return quotesByCityId.entrySet().stream()
                .map(x->new QuoteCity(x.getKey(), Collections.min(x.getValue(), Comparator.comparing(Quote::getMinPrice))))
                .collect(Collectors.toList());
    }

    private String findCity(int destinationId, List<Place> places){

        return places.stream()
                .filter(p -> p.getPlaceId() == destinationId)
                .findAny().orElse(new Place()).getCityId();
    }

    private Set<String> findComparableCities(List<List<QuoteCity>> quotesByDestination){

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

    private List<Set<String>> getCitiesFromQuotes(List<List<QuoteCity>> quotesByDestination){
        List<Set<String>> citiesFromQuotes = new ArrayList<>();
        for (List<QuoteCity> l : quotesByDestination) {
            citiesFromQuotes.add(
                    l.stream()
                    .map(QuoteCity::getCityId)
                    .collect(Collectors.toSet())
            );
        }
        return citiesFromQuotes;
    }

    private String findCheapestCityOverall(List<List<QuoteCity>> quotesByCity){

        List<QuoteCity> joinedQuotesByDestination = joinListsOfQuotesByCity(quotesByCity);

        Map<String, Double> sumPricesPerCity = joinedQuotesByDestination.stream()
                .collect(Collectors.groupingBy(QuoteCity::getCityId,
                        Collectors.summingDouble(q -> q.getQuote().getMinPrice())));

        return getMinKey(sumPricesPerCity);
    }

    private List<QuoteCity> joinListsOfQuotesByCity(List<List<QuoteCity>> quotesByCity){
        Stream<QuoteCity> stream = Stream.of();
        for (List<QuoteCity> l: quotesByCity)
            stream = Stream.concat(stream, l.stream());

        return stream.collect(Collectors.toList());
    }

    private String getMinKey(Map<String, Double> map, String... keys) {
        String minKey = null;
        double minValue = Double.MAX_VALUE;
        for(String key : keys) {
            double value = map.get(key);
            if(value < minValue) {
                minValue = value;
                minKey = key;
            }
        }
        return minKey;
    }
}
