package ru.uminsky.weatheranalyzer.service;

import ru.uminsky.weatheranalyzer.model.Weather;

import java.io.IOException;
import java.time.LocalDate;

public interface WeatherService {

	Weather getWeatherDataCity(String city) throws IOException;

    Weather saveWeather(Weather weather);

    Weather findTopWeather();

    Double findAverageTemperatureBetweenDates(LocalDate startDate, LocalDate endDate, String city);

}
