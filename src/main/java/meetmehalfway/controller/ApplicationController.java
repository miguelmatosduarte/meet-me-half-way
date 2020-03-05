package meetmehalfway.controller;

import meetmehalfway.model.City;
import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.model.skyscanner.browseQuotes.Quote;
import meetmehalfway.utils.QuoteComparer;
import meetmehalfway.utils.SkyScannerAPIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private SkyScannerAPIUtils skyScannerAPIUtils;

    @GetMapping("/")
    public String index() {
        return "Welcome to Meet Me Halfway!";
    }

    @GetMapping("/cities")
    public List<City> availableCities(){
        return skyScannerAPIUtils.getPlacesGeo().getAvailableCities();
    }

    @RequestMapping("/search")
    @ResponseBody
    public Result search(@RequestBody Passengers passengers){
        QuoteComparer quoteComparer = new QuoteComparer(skyScannerAPIUtils);
        quoteComparer.loadQuotes(passengers);
        List<Quote> quotes = quoteComparer.compareQuotes();
        return quoteComparer.quotesToResult(quotes);
    }

}