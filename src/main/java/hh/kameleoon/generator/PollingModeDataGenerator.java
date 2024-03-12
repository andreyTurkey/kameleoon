package hh.kameleoon.generator;

import hh.kameleoon.PollingMode;
import hh.kameleoon.WeatherClient;
import hh.kameleoon.exception.NotAvailableException;
import hh.kameleoon.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollingModeDataGenerator implements PollingMode {

    private boolean isModeOn = false;
    private List<WeatherData> savedData = new ArrayList<>();

    @Autowired
    private WeatherClient weatherClient;

    @Override
    public List<WeatherData> addNewLocation(String city,
                                            Integer stateCodeForUsa,
                                            Integer countryCode) throws NotAvailableException {
        if (isExist(city, stateCodeForUsa, countryCode)) return savedData;

        if (savedData.size() >= 10) {
            savedData.remove(0);
            savedData.add(weatherClient.getWeatherData(city, stateCodeForUsa, countryCode));
        } else {
            savedData.add(weatherClient.getWeatherData(city, stateCodeForUsa, countryCode));
        }
        return savedData;
    }

    @Override
    public List<WeatherData> getData() {
        return savedData;
    }

    @Scheduled(fixedDelayString = "${task.schedule.delay}")
    private void updateData() {
        if (isModeOn) {
            savedData.forEach(object -> weatherClient.getLiteWeatherData(
                    object.getCoord().get("lat"),
                    object.getCoord().get("lon")));
        }
    }

    private boolean isExist(String city,
                            Integer stateCodeForUsa,
                            Integer countryCode) throws NotAvailableException {
        WeatherData weatherData = weatherClient.getWeatherData(city, stateCodeForUsa, countryCode);

        return savedData.stream().anyMatch(object ->
                object.getCoord().get("lat").equals(weatherData.getCoord().get("lat")) &&
                        object.getCoord().get("lon").equals(weatherData.getCoord().get("lon")));
    }

    public void setIsModeOn() {
        this.isModeOn = true;
    }

    public void setIsModeOff() {
        this.isModeOn = false;
    }

    public boolean getIsModeOn() {
        return isModeOn;
    }
}
