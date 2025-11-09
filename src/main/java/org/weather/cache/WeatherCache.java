package org.weather.cache;

import org.weather.model.WeatherDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class WeatherCache {
    private final LinkedHashMap<String, WeatherDto> cache = new LinkedHashMap<>();

    public synchronized void put(String city, WeatherDto data) {
        if (cache.size() >= 10) {
            String firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
        }
        cache.put(city.toLowerCase(), data);
    }

    public synchronized WeatherDto get(String city) {
        WeatherDto data = cache.get(city.toLowerCase());
        if (data != null && data.isFresh()) {
            return data;
        }
        return null;
    }

    public synchronized Collection<String> getCities() {
        return new ArrayList<>(cache.keySet());
    }
}
