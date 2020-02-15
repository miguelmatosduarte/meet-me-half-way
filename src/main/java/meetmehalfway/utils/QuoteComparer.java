package meetmehalfway.utils;

import meetmehalfway.model.City;
import meetmehalfway.model.api.result.PassengerResult;
import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.search.Passenger;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.model.skyscanner.response.BrowseQuotesResponse;
import meetmehalfway.model.skyscanner.response.Place;
import meetmehalfway.model.skyscanner.response.Quote;
import meetmehalfway.model.QuoteCity;
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

@Service
public class QuoteComparer {

    @Autowired
    private SkyScannerAPIUtils skyScannerAPIUtils;

    private List<BrowseQuotesResponse> quoteResponses;

    public City getCheapestCity() {
        return cheapestCity;
    }

    public void setCheapestCity(City cheapestCity) {
        this.cheapestCity = cheapestCity;
    }

    private City cheapestCity;

    private Set<Place> places;

    private  BrowseQuotesResponse browseQuotes(Passenger passenger){
        BrowseQuotesResponse browseQuotesResponse = skyScannerAPIUtils.browseQuotes("PT", "EUR", "pt-PT", passenger.getOrigin(), "anywhere", passenger.getDepartureDate());
        browseQuotesResponse.getQuotes().forEach(
                q -> q.setPassengerNumber(passenger.getNumber())
        );
        return browseQuotesResponse;
    }

    public void loadFromPassengers(Passengers passengers){
        this.quoteResponses = passengers.getPassengers().stream()
                .map(this::browseQuotes)
                .collect(Collectors.toList())
                ;
    }

    public Result quotesToResult(List<Quote> quotes){
        Result result = new Result();
        //result.setCity(findCity(quotes.get(0).getOutboundLeg().getDestinationId(), quotes.get(0)));
        return result;
    }

    public List<Quote> compareQuotes(){
        List<Place> places = new ArrayList<>();

        List<List<QuoteCity>> quotesByCity = new ArrayList<>();

        quoteResponses.forEach(qr -> places.addAll(qr.getPlaces()));
        this.places = new HashSet<>(places);

        quoteResponses.forEach(qr ->
                quotesByCity.add(
                        minPricesPerCity(qr.getQuotes())
                ));

        Set<String> comparableCities = findComparableCities(quotesByCity);

        quotesByCity.forEach(
          list -> list.removeIf(s -> !comparableCities.contains(s.getCityId()))
        );

        setCheapestCity(findCheapestCityOverall(quotesByCity));

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

    private  List<QuoteCity> minPricesPerCity(List<Quote> quotes){
        Map<String, List<Quote>> quotesByCityId =
                quotes.stream()
                        .collect(Collectors.groupingBy(p ->
                                findCity(p.getOutboundLeg().getDestinationId(), places).getCityID()
                        ));

        return quotesByCityId.entrySet().stream()
                .map(x->new QuoteCity(x.getKey(), Collections.min(x.getValue(), Comparator.comparing(Quote::getMinPrice))))
                .collect(Collectors.toList());
    }

    private City findCity(int destinationId, Set<Place> places){
        Place place = places.stream()
                .filter(p -> p.getPlaceId() == destinationId)
                .findAny().orElse(new Place());
        City city = new City();
        city.setCityID(place.getCityId());
        city.setCityName(place.getCityName());
        return city;
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

    private City findCheapestCityOverall(List<List<QuoteCity>> quotesByCity){

        List<QuoteCity> joinedQuotesByDestination = joinListsOfQuotesByCity(quotesByCity);

        Map<String, Double> sumPricesPerCity = joinedQuotesByDestination.stream()
                .collect(Collectors.groupingBy(QuoteCity::getCityId,
                        Collectors.summingDouble(q -> q.getQuote().getMinPrice())));

        return findCity(getMinKey(sumPricesPerCity), places);
    }

    private List<QuoteCity> joinListsOfQuotesByCity(List<List<QuoteCity>> quotesByCity){
        Stream<QuoteCity> stream = Stream.of();
        for (List<QuoteCity> l: quotesByCity)
            stream = Stream.concat(stream, l.stream());

        return stream.collect(Collectors.toList());
    }

    private String getMinKey(Map<String, Double> map) {
        String minKey = null;
        double minValue = Double.MAX_VALUE;
        for(String key : map.keySet()) {
            double value = map.get(key);
            if(value < minValue) {
                minValue = value;
                minKey = key;
            }
        }
        return minKey;
    }
}
