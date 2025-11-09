package org.weather.manager;

import org.weather.WeatherMode;
import org.weather.service.WeatherSDK;
import org.weather.service.impl.WeatherSDKImpl;

import java.util.concurrent.ConcurrentHashMap;

public class WeatherManager {

    private static final ConcurrentHashMap<String, WeatherSDKImpl> instances = new ConcurrentHashMap<>();

    public static synchronized WeatherSDK getInstance(String apiKey, WeatherMode mode) {
        if (instances.containsKey(apiKey)) {
            return instances.get(apiKey);
        }
        WeatherSDKImpl sdk = new WeatherSDKImpl(apiKey, mode);
        instances.put(apiKey, sdk);
        return sdk;
    }

    public static synchronized void removeInstance(String apiKey) {
        WeatherSDKImpl sdk = instances.remove(apiKey);
        if (sdk != null) sdk.destroy();
    }
}
