package hh.kameleoon.model;

import lombok.Data;

@Data
public class MainParameters {

    private Double temp;
    private Double feels_like;
    private Double temp_min;
    private Double temp_max;
    private Integer pressure;
    private Integer humidity;
    private Integer sea_level;
    private  Integer grnd_level;
}
