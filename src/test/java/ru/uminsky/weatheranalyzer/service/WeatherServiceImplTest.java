package ru.uminsky.weatheranalyzer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.uminsky.weatheranalyzer.model.Weather;
import ru.uminsky.weatheranalyzer.parsers.WeatherParser;
import ru.uminsky.weatheranalyzer.repository.WeatherRepository;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherParser parser;

    private String city;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void testSaveWeather() {

        Weather weatherToSave = new Weather();
        when(weatherRepository.save(weatherToSave)).thenReturn(weatherToSave);

        Weather result = weatherService.saveWeather(weatherToSave);

        assertEquals(weatherToSave, result);
        verify(weatherRepository, times(1)).save(weatherToSave);
    }

    @Test
    void testFindTopWeather() {

        Weather expectedWeather = new Weather();
        when(weatherRepository.findTopByCityOrderByIdDesc(city)).thenReturn(expectedWeather);

        Weather result = weatherService.findTopWeather(city);

        assertEquals(expectedWeather, result);
        verify(weatherRepository, times(1)).findTopByCityOrderByIdDesc(city);
    }

    @Test
    void testFindAverageTemperatureBetweenDates() {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        Double expectedAverageTemperature = 25.5;
        when(weatherRepository.findAverageTemperatureBetweenDates(startDate, endDate, city)).thenReturn(expectedAverageTemperature);

        Double result = weatherService.findAverageTemperatureBetweenDates(startDate, endDate, city);

        assertEquals(expectedAverageTemperature, result);
        verify(weatherRepository, times(1)).findAverageTemperatureBetweenDates(startDate, endDate, city);
    }
}
