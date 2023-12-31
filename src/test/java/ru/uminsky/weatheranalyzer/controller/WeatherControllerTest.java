package ru.uminsky.weatheranalyzer.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.uminsky.weatheranalyzer.model.Weather;
import ru.uminsky.weatheranalyzer.service.WeatherService;

import java.time.LocalDate;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String city;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetCurrentTopWeather() throws Exception {

        Weather mockWeather = new Weather();
        Mockito.when(weatherService.findTopWeather(city)).thenReturn(mockWeather);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/current"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetCurrentTopWeatherNoData() throws Exception {

        Mockito.when(weatherService.findTopWeather(city)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/current"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"));
    }

    @Test
    public void testGetAverageTemperature() throws Exception {

        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        Double mockAverageTemperature = 25.0;
        Mockito.when(weatherService.findAverageTemperatureBetweenDates(startDate, endDate, city)).thenReturn(mockAverageTemperature);

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/average_temperature")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("weather/average_temperature"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("startDate", "endDate", "averageTemperature"));
    }

    @Test
    public void testGetAverageTemperatureInvalidDateRange() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/weather/average_temperature")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().minusDays(1).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"));
    }
}
