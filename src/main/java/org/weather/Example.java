package org.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.weather.exc.WeatherException;
import org.weather.manager.WeatherManager;
import org.weather.service.WeatherSDK;

public class Example {

    public static void main(String[] args) {

        try {
            //SDK initialization
            WeatherSDK sdk = WeatherManager.getInstance("YOUR_API_KEY", WeatherMode.ON_DEMAND);

            //Getting weather data in JSON format
            String weatherJson = sdk.getWeatherJson("Minsk");

            //Within 10 minutes the data is taken from the cache
            String weatherCache = sdk.getWeatherJson("Minsk");

            System.out.println(weatherJson);

            //SDK remove
            WeatherManager.removeInstance("YOUR_API_KEY");

        } catch (WeatherException | JsonProcessingException exc) {
            System.err.println("Error: " + exc.getMessage());
        }
    }
}

