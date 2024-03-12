package hh.kameleoon.model;

import lombok.Data;

import java.util.Map;

@Data
public class WeatherData {

    private Map<String, Double> coord;
    private Weather[] weather;
    private String base;
    private MainParameters main;
    private Integer visibility;
    private Wind wind;
    private Map<String, Double> rain;
    private Map<String, Integer> clouds;
    private Integer dt;
    private Sys sys;
    private Integer timezone;
    private Integer id;
    private String name;
    private Integer cod;
}
