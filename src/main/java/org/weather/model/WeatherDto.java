package org.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class WeatherDto {

    private Map<String, String> weather;

    private Map<String, Double> temperature;

    private int visibility;

    private Map<String, Double> wind;

    private long datetime;

    private Map<String, Long> sys;

    private int timezone;

    private String name;

    @JsonIgnore
    private LocalDateTime receiptTime;

    public boolean isFresh() {
        return receiptTime.isAfter(LocalDateTime.now().minusMinutes(10));
    }
}
