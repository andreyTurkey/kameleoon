package hh.kameleoon;

import com.fasterxml.jackson.databind.ObjectMapper;
import hh.kameleoon.exception.NotAvailableException;
import hh.kameleoon.model.Geocoordinate;
import hh.kameleoon.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherClient {

    private final RestTemplate rest;
    private @Value("${geocoding-server-with-usa.url}") String uriWithUsa;
    private @Value("${geocoding-server.url}") String uri;
    private @Value("${geocoding-server-data}") String mainUri;
    private @Value("${API_KEY}") String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public WeatherClient(RestTemplateBuilder builder) {
        rest = builder.build();
    }

    public WeatherData getWeatherData(String city,
                                            Integer stateCodeForUsa,
                                            Integer countryCode) throws NotAvailableException {
        List<Geocoordinate> geocoordinates = getLatitudeAndLongitude(city, stateCodeForUsa, countryCode, apiKey);

        String path = String.format(mainUri, geocoordinates.get(0).getLat(), geocoordinates.get(0).getLon(), apiKey);

        Object objects = makeAndSendRequestWeatherData(path);
        return converterToWeatherData(objects);
    }

    public WeatherData getLiteWeatherData(Double lat, Double lon) {
        String path = String.format(mainUri, lat, lon, apiKey);
        Object objects = makeAndSendRequestWeatherData(path);
        return converterToWeatherData(objects);
    }

    private List<Geocoordinate> getLatitudeAndLongitude(String city,
                                                        Integer stateCodeForUsa,
                                                        Integer countryCode,
                                                        String apiKey) throws NotAvailableException {
        String path;
        if (stateCodeForUsa != null) {
            path = String.format(uriWithUsa, city.toLowerCase(), stateCodeForUsa, countryCode, apiKey);
        } else {
            path = String.format(uri, city.toLowerCase(), countryCode, apiKey);
        }
        List<Geocoordinate> geocoordinates = converterToGeocoordinateList(makeAndSendRequest(path));
        if (geocoordinates.size() == 0) {
            throw new NotAvailableException("Check the correct spelling of the locality");
        }
        return geocoordinates;
    }

    private Object[] makeAndSendRequest(String path) {
        ResponseEntity<Object[]> responseEntity = rest.getForEntity(path, Object[].class);

        return responseEntity.getBody();
    }

    private Object makeAndSendRequestWeatherData(String path) {
        ResponseEntity<Object> responseEntity = rest.getForEntity(path, Object.class);
        return responseEntity.getBody();
    }

    private List<Geocoordinate> converterToGeocoordinateList(Object[] objects) {
        return Arrays.stream(objects)
                .map(object -> objectMapper.convertValue(object, Geocoordinate.class))
                .collect(Collectors.toList());
    }

    private WeatherData converterToWeatherData(Object object) {
        return objectMapper.convertValue(object, WeatherData.class);
    }
}