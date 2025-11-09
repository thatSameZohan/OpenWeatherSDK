package org.weather.mapper;

import org.weather.model.ResponseWeatherDto;
import org.weather.model.WeatherDto;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeatherMapper {

    public WeatherDto toWeatherDto(ResponseWeatherDto response) {

        WeatherDto weatherDto = new WeatherDto();

        Map<String, String> weatherMap = new LinkedHashMap<>();
        weatherMap.put("main", response.getWeather().getFirst().getMain());
        weatherMap.put("description", response.getWeather().getFirst().getDescription());
        weatherDto.setWeather(weatherMap);

        Map<String, Double> tempMap = new LinkedHashMap<>();
        tempMap.put("temp", response.getTemperature().getTemp());
        tempMap.put( "feels_like", response.getTemperature().getFeelsLike());
        weatherDto.setTemperature(tempMap);

        weatherDto.setVisibility(response.getVisibility());

        Map<String, Double> windMap = new LinkedHashMap<>();
        windMap.put("speed", response.getWind().getSpeed());
        weatherDto.setWind(windMap);

        weatherDto.setDatetime(response.getDatetime());
        Map<String, Long> sysMap = new LinkedHashMap<>();
        sysMap.put("sunrise", response.getSys().getSunrise());
        sysMap.put("sunset", response.getSys().getSunset());
        weatherDto.setSys(sysMap);

        weatherDto.setTimezone(response.getTimezone());

        weatherDto.setName(response.getName());

        weatherDto.setReceiptTime(LocalDateTime.now());

        return weatherDto;
    }
}
