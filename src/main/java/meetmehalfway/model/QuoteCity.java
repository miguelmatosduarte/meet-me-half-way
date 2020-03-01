package meetmehalfway.model;

import meetmehalfway.model.skyscanner.browseQuotes.Quote;

public class    QuoteCity {
    private String cityId;
    private Quote quote;

    public QuoteCity(String cityId, Quote quote){
        this.quote = quote;
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
