package hh.kameleoon;

import hh.kameleoon.exception.NotAvailableException;
import hh.kameleoon.model.WeatherData;

import java.util.List;

public interface PollingMode {

    List<WeatherData> addNewLocation(String city,
                                     Integer stateCodeForUsa,
                                     Integer countryCode) throws NotAvailableException;

    List<WeatherData> getData();

    void setIsModeOn();

    void setIsModeOff();

    boolean getIsModeOn();

}
