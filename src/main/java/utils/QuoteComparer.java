package utils;

import json.schema.browsequotesresponse.BrowseQuotesResponse;
import json.schema.browsequotesresponse.Quote;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QuoteComparer {

    private List<BrowseQuotesResponse> quoteResponses;

    public QuoteComparer(List<BrowseQuotesResponse> quoteResponses){
        this.quoteResponses = quoteResponses;
    }

    public void compareQuotes(){
        // First let's retain only the min prices for quotes with the same destinationID

    }

    private List<Quote> minPricesPereDestination(List<Quote> quotes){
        quotes.stream()
                .collect(Collectors.toMap(Quote::getOutboundLeg.getDestinationId, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Quote::getMinPrice))));
    }
}
