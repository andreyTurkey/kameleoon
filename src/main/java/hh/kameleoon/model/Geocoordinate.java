package hh.kameleoon.model;

import lombok.Data;

import java.util.Map;


@Data
public class Geocoordinate {

    private String name;
    private Map<String, String> local_names;
    private Double lat;
    private Double lon;
    private String country;
    private String state;
}
