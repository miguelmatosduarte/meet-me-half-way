package meetmehalfway.controller;

import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.model.skyscanner.response.Quote;
import meetmehalfway.utils.QuoteComparer;
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
    private QuoteComparer quoteComparer;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/search")
    @ResponseBody
    public Result search(@RequestBody Passengers passengers){
        quoteComparer.loadFromPassengers(passengers);
        List<Quote> quotes = quoteComparer.compareQuotes();
        return quoteComparer.quotesToResult(quotes);
    }

}