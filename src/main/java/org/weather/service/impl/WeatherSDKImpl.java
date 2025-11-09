package org.weather.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.weather.WeatherMode;
import org.weather.cache.WeatherCache;
import org.weather.exc.WeatherException;
import org.weather.mapper.WeatherMapper;
import org.weather.model.ResponseWeatherDto;
import org.weather.model.WeatherDto;
import org.weather.service.WeatherSDK;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherSDKImpl implements WeatherSDK {

    private final String apiKey;

    private final WeatherMode mode;

    private final WeatherCache cache = new WeatherCache();

    private ScheduledExecutorService executor;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public WeatherSDKImpl(String apiKey, WeatherMode mode) {
        this.apiKey = apiKey;
        this.mode = mode;

        if (mode == WeatherMode.POOLING) {
            startPolling();
        }
    }

    private void startPolling() {
        executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {

            for (String city : cache.getCities()) {

                try {
                    WeatherDto data = fetchWeather(city);
                    cache.put(city, data);

                } catch (Exception e) {
                    System.err.println("Update error " + city + ": " + e.getMessage());
                }
            }
        }, 0, 10, TimeUnit.MINUTES);
    }

    @Override
    public String getWeatherJson(String city) throws WeatherException, JsonProcessingException {

        WeatherDto cached = cache.get(city);

        if (cached != null){
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cached);
        }

        WeatherDto fresh = fetchWeather(city);

        cache.put(city, fresh);

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(fresh);
    }

    private WeatherDto fetchWeather(String city) throws WeatherException {
        try {
            String url = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=standart&lang=en",
                    city, apiKey
            );
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200)
                throw new WeatherException("Error API: " + response.statusCode());

            String body = response.body();

            WeatherMapper weatherMapper = new WeatherMapper();

            ResponseWeatherDto responseWeatherDto = objectMapper.readValue(body, ResponseWeatherDto.class);

            return weatherMapper.toWeatherDto(responseWeatherDto);

        } catch (Exception e) {
            throw new WeatherException("Failed to retrieve data: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        if (executor != null)
            executor.shutdown();
    }

    private String getKey() {
        return apiKey;
    }
}
