package utils;

import json.schema.browsequotesresponse.BrowseQuotesResponse;
import json.schema.browsequotesresponse.Place;
import json.schema.browsequotesresponse.Quote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuoteComparer {

    private List<BrowseQuotesResponse> quoteResponses;

    public QuoteComparer(List<BrowseQuotesResponse> quoteResponses){
        this.quoteResponses = quoteResponses;
    }

    public void compareQuotes(){
        List<List<QuoteCity>> quotesByDestination = new ArrayList<>();

        quoteResponses.forEach(
                qr -> quotesByDestination.add(
                        minPricesPerCity(qr.getQuotes(), qr.getPlaces())
                )
        );

        getBestQuotes(quotesByDestination);
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
}
