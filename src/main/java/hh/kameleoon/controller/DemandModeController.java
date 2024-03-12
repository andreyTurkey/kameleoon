package hh.kameleoon.controller;

import hh.kameleoon.DemandMode;
import hh.kameleoon.exception.NotAvailableException;
import hh.kameleoon.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandMode")
public class DemandModeController {

    @Autowired
    private DemandMode demandMode;

    @PostMapping("/new")
    public List<WeatherData> addNewLocation(@RequestParam String city,
                                            @RequestParam String countryCode) throws NotAvailableException {
        Integer newCountryCode;
        if (countryCode.length() > 9) {
            throw new NotAvailableException("Check the correct spelling of the country code");
        } else {
            newCountryCode = Integer.valueOf(countryCode);
        }
        return demandMode.addNewLocation(city, null, newCountryCode);
    }

    @GetMapping
    public List<WeatherData> getWeatherJson()  {
        return demandMode.getData();
    }
}
