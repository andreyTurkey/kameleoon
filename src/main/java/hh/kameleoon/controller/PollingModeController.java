package hh.kameleoon.controller;

import hh.kameleoon.PollingMode;
import hh.kameleoon.exception.NotAvailableException;
import hh.kameleoon.model.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pollingMode")
@Slf4j
public class PollingModeController {

    @Autowired
    private PollingMode pollingMode;

    @PostMapping("/on")
    public void setIsModeOn() {
        pollingMode.setIsModeOn();
        log.debug("Value isModeOn = " + pollingMode.getIsModeOn());
    }

    @PostMapping("/off")
    public void setIsModeOff()  {
        pollingMode.setIsModeOff();
        log.debug("Value isModeOn = " + pollingMode.getIsModeOn());
    }

    @PostMapping("/new")
    public List<WeatherData> addNewLocation(@RequestParam String city,
                                            @RequestParam String countryCode) throws NotAvailableException {
        Integer newCountryCode;
        if (countryCode.length() > 9) {
            throw new NotAvailableException("Check the correct spelling of the country code");
        } else {
            newCountryCode = Integer.valueOf(countryCode);
        }
        return pollingMode.addNewLocation(city, null, newCountryCode);
    }

    @GetMapping
    public List<WeatherData> getWeatherJson()  {
        return pollingMode.getData();
    }
}
