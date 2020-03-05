package meetmehalfway.model;

import meetmehalfway.model.skyscanner.browseQuotes.Quote;
import meetmehalfway.model.skyscanner.geo.City;

public class    QuoteCity {
    private City city;
    private Quote quote;

    public QuoteCity(City city, Quote quote){
        this.quote = quote;
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
