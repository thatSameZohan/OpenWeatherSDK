package org.weather.service;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface WeatherSDK {

    String getWeatherJson(String city) throws JsonProcessingException;

    void destroy();
}
