package org.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ResponseWeatherDto {

    private List<Weather> weather;

    @JsonProperty("main")
    private Temperature temperature;

    private int visibility;

    private Wind wind;

    @JsonProperty("dt")
    private long datetime;

    private Sys sys;

    private int timezone;

    private String name;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class Weather {

        private String main;

        private String description;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class Temperature {

        private double temp;

        @JsonProperty("feels_like")
        private double feelsLike;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class Wind {

        private double speed;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class Sys {

        private long sunrise;

        private long sunset;
    }
}
