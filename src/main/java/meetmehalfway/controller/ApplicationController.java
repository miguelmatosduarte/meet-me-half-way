package meetmehalfway.controller;

import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.utils.QuoteComparer;
import meetmehalfway.utils.SkyScannerAPIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApplicationController {

    @Autowired
    private SkyScannerAPIUtils skyScannerAPIUtils;

    @GetMapping("/")
    public String index() {
        return "Welcome to Meet Me Halfway!";
    }

    @GetMapping("/cities")
    public Map<String, String> availableCities(){

        Map<String, String> citiesCountries = new HashMap<>();

        skyScannerAPIUtils.geo().getContinents().forEach(
                continent -> continent.getCountries().forEach(
                        country -> country.getCities().forEach(
                                city -> citiesCountries.put(city.getName(), country.getName())
                        )
                )
        );

        return citiesCountries;
    }

    @RequestMapping("/search")
    @ResponseBody
    public Result search(@RequestBody Passengers passengers){
        QuoteComparer quoteComparer = new QuoteComparer(passengers,skyScannerAPIUtils);
        return quoteComparer.compareQuotes();
    }

}