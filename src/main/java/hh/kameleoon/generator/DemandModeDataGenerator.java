package hh.kameleoon.generator;

import hh.kameleoon.DemandMode;
import hh.kameleoon.WeatherClient;
import hh.kameleoon.exception.NotAvailableException;
import hh.kameleoon.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemandModeDataGenerator implements DemandMode {

    private List<WeatherData> savedData = new ArrayList<>();
    private LocalDateTime timeCounter = LocalDateTime.now();

    @Autowired
    private WeatherClient weatherClient;

    @Override
    public List<WeatherData> addNewLocation(String city,
                                            Integer stateCodeForUsa,
                                            Integer countryCode) throws NotAvailableException {
        if (isExist(city, stateCodeForUsa, countryCode)) return savedData;

        if (savedData.size() >= 10) {
            savedData.remove(0);
            if (timeCounter.isEqual(timeCounter.plusMinutes(10))) {
                updateData();
            }
            savedData.add(weatherClient.getWeatherData(city, stateCodeForUsa, countryCode));
            timeCounter = LocalDateTime.now();
        } else {
            savedData.add(weatherClient.getWeatherData(city, stateCodeForUsa, countryCode));
        }
        return savedData;
    }

    @Override
    public List<WeatherData> getData() {
        if (timeCounter.isEqual(timeCounter.plusMinutes(10))) {
            updateData();
        }
        return savedData;
    }

    private void updateData() {
        savedData.forEach(object -> weatherClient.getLiteWeatherData(
                object.getCoord().get("lat"),
                object.getCoord().get("lon")));
    }

    private boolean isExist(String city,
                            Integer stateCodeForUsa,
                            Integer countryCode) throws NotAvailableException {
        WeatherData weatherData = weatherClient.getWeatherData(city, stateCodeForUsa, countryCode);

        return savedData.stream().anyMatch(object ->
                object.getCoord().get("lat").equals(weatherData.getCoord().get("lat")) &&
                object.getCoord().get("lon").equals(weatherData.getCoord().get("lon")));
    }
}
