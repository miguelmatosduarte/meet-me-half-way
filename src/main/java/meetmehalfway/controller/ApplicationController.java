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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private SkyScannerAPIUtils skyScannerAPIUtils;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public String index() {
        return "Welcome to Meet Me Halfway!";
    }

    @GetMapping("/cities")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<CitySelect> availableCities(){

        List<CitySelect> availableCities = new ArrayList<>();

        skyScannerAPIUtils.geo().getContinents().forEach(
                continent -> continent.getCountries().forEach(
                        country -> country.getCities().forEach(
                                city -> availableCities.add(
                                        new CitySelect()
                                                .withCityName(city.getName())
                                                .withCoutryName(city.getCountryId())
                                                .withid(city.getId())
                                )
                        )
                )
        );

        return availableCities;
    }

    @RequestMapping("/search")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public Result search(@RequestBody Passengers passengers){
        QuoteComparer quoteComparer = new QuoteComparer(passengers,skyScannerAPIUtils);
        return quoteComparer.compareQuotes();
    }
}