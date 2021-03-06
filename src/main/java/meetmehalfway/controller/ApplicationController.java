package meetmehalfway.controller;

import meetmehalfway.model.api.result.Result;
import meetmehalfway.model.api.search.CitySelect;
import meetmehalfway.model.api.search.Passengers;
import meetmehalfway.utils.QuoteComparer;
import meetmehalfway.utils.SkyScannerAPIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private SkyScannerAPIUtils skyScannerAPIUtils;

    @GetMapping("/")
    @CrossOrigin(origins = {"https://meetmehalfway.site", "http://localhost:3000"})
    public String index() {
        return "Welcome to Meet Me Halfway!";
    }

    @GetMapping("/cities")
    @CrossOrigin(origins = {"https://meetmehalfway.site", "http://localhost:3000"})
    public List<CitySelect> availableCities(){

        return skyScannerAPIUtils.getAvailableCities();
    }

    @RequestMapping("/search")
    @CrossOrigin(origins = {"https://meetmehalfway.site", "http://localhost:3000"})
    @ResponseBody
    public Result search(@RequestBody Passengers passengers){
        QuoteComparer quoteComparer = new QuoteComparer(passengers,skyScannerAPIUtils);
        return quoteComparer.compareQuotes();
    }
}